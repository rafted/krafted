package server

import chat.ChatComponent
import chat.color.ChatColor
import com.akuleshov7.ktoml.Toml
import event.EventBus
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import kotlinx.serialization.Serializable
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import logic.listeners.ConnectionListener
import logic.listeners.HandshakeListener
import logic.listeners.LoginListener
import logic.listeners.PingListener
import org.tinylog.kotlin.Logger
import protocol.packet.impl.status.clientbound.Players
import protocol.packet.impl.status.clientbound.Response
import protocol.packet.impl.status.clientbound.Version
import server.connection.Connection
import server.connection.ConnectionClosedEvent
import java.io.File

@Serializable
data class ServerConfig(
    val host: String = "0.0.0.0",
    val port: Int = 25565,
    var description: ChatComponent = ChatComponent()
        .text("Krafted - Server")
        .color(ChatColor.CYAN),
    var maxPlayers: Int = 20,
    var favicon: String = ""
)

object Server {
    lateinit var config: ServerConfig
    lateinit var format: StringFormat

    val connections = mutableListOf<Connection>()
    val eventBus: EventBus = EventBus

    fun start(format: StringFormat) {
        this.format = format
        this.config = loadServerConfig()

        this.registerListeners()

        val bossGroup = NioEventLoopGroup(1)
        val workerGroup = NioEventLoopGroup()

        try {

            val bootstrap = ServerBootstrap().apply {
                this.group(bossGroup, workerGroup)
                this.channel(NioServerSocketChannel::class.java)
                this.handler(LoggingHandler(LogLevel.DEBUG))
                this.childHandler(ServerInitializer)
            }

            val channel = bootstrap.bind(this.config.host, this.config.port)
                .sync()
                .channel()

            channel.closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    private fun registerListeners() {
        EventBus.subscribe(HandshakeListener)
        EventBus.subscribe(ConnectionListener)
        EventBus.subscribe(PingListener)
        EventBus.subscribe(LoginListener)
    }

    fun findConnection(channel: Channel): Connection? {
        return this.connections.find { it.channel == channel }
    }

    fun closeConnection(connection: Connection) {
        Logger.info("Removing Connection: ${connection.id}")

        this.connections.remove(connection)
        this.eventBus.post(ConnectionClosedEvent(connection))
    }

    private fun createServerConfig(file: File) {
        file.createNewFile()
        file.writeText(
            format.encodeToString(ServerConfig())
        )
    }

    private fun loadServerConfig(): ServerConfig {
        val file = File(
            "server.${
                format.javaClass
                    .simpleName
                    .lowercase()
                    .replace("impl", "")
            }"
        )

        if (!file.exists()) {
            createServerConfig(file)
        }

        val contents = file.readText()

        return format.decodeFromString(contents)
    }

    fun makeStatusRespose(): Response {
        return Response(
            version = Version(
                protocol = 47,
                name = "1.8.9"
            ),
            players = Players(config.maxPlayers, 0, emptyList()),
            description = config.description,
            favicon = config.favicon
        )
    }
}
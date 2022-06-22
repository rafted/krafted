package server

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import event.EventBus
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import logic.listeners.ConnectionListener
import logic.listeners.HandshakeListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import protocol.packet.impl.status.Description
import protocol.packet.impl.status.Players
import protocol.packet.impl.status.Response
import protocol.packet.impl.status.Version
import server.connection.Connection
import server.connection.ConnectionClosedEvent

data class ServerConfig(
    val host: String,
    val port: Int,
)

object Server {
    lateinit var config: ServerConfig

    val logger: Logger = LoggerFactory.getLogger(Server::class.java)
    val connections = mutableListOf<Connection>()
    val eventBus: EventBus = EventBus
    val gson: Gson = GsonBuilder().create()

    fun start(config: ServerConfig) {
        this.config = config

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
        eventBus.subscribe(HandshakeListener)
        eventBus.subscribe(ConnectionListener)
    }

    fun findConnection(channel: Channel): Connection? {
        return this.connections.find { it.channel == channel }
    }

    fun closeConnection(connection: Connection) {
        this.connections.remove(connection)
        this.logger.info("Removed Connection: ${connection.id}")

        this.eventBus.post(ConnectionClosedEvent(connection))
    }

    fun makeStatusRespose(): Response {
        return Response(
            version = Version(
                protocol = 47,
                name = "1.8.9"
            ),
            players = Players(20, 0, emptyList()),
            description = Description("A Minecraft Server"),
            favicon = ""
        )
    }
}

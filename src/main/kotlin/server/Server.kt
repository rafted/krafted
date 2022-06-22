package server

import chat.ChatComponent
import event.EventBus
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import logic.listeners.ConnectionListener
import logic.listeners.HandshakeListener
import logic.listeners.PingListener
import org.tinylog.kotlin.Logger
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

    val connections = mutableListOf<Connection>()
    val eventBus: EventBus = EventBus

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
        EventBus.subscribe(HandshakeListener)
        EventBus.subscribe(ConnectionListener)
        EventBus.subscribe(PingListener)
    }

    fun findConnection(channel: Channel): Connection? {
        return this.connections.find { it.channel == channel }
    }

    fun closeConnection(connection: Connection) {
        Logger.info("Removing Connection: ${connection.id}")

        this.connections.remove(connection)
        this.eventBus.post(ConnectionClosedEvent(connection))
    }

    fun makeStatusRespose(): Response {
        return Response(
            version = Version(
                protocol = 47,
                name = "1.8.9"
            ),
            players = Players(20, 0, emptyList()),
            description = ChatComponent().text("Krafted - Server"),
            favicon = ""
        )
    }
}

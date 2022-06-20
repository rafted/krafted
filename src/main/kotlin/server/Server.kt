package server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import server.connection.Connection

data class ServerConfig(
    val host: String,
    val port: Int,
)

object Server {
    lateinit var config: ServerConfig

    val logger: Logger = LoggerFactory.getLogger(Server::class.java)
    val connections = mutableListOf<Connection>()

    fun start(config: ServerConfig) {
        this.config = config

        val bossGroup = NioEventLoopGroup(1)
        val workerGroup = NioEventLoopGroup()

        try {

            val bootstrap = ServerBootstrap().apply {
                this.group(bossGroup, workerGroup)
                this.channel(NioServerSocketChannel::class.java)
                this.handler(LoggingHandler(LogLevel.DEBUG))
                this.childHandler(ServerInitializer())
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
}

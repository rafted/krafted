package server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import server.connection.Connection

data class ServerConfig(
    val port: Int,
    val host: String
)

class Server(config: ServerConfig) {

 companion object {
   lateinit var instance: Server
 }

  val logger: Logger = LoggerFactory.getLogger(Server::class.java)
  val connections = mutableListOf<Connection>()

  init {
    instance = this
  }

  fun start() {
    val bossGroup = NioEventLoopGroup(1)
    val workerGroup = NioEventLoopGroup()

    try {

      val bootstrap = ServerBootstrap()
      bootstrap.group(bossGroup, workerGroup)
      bootstrap.channel(NioServerSocketChannel::class.java)
      bootstrap.handler(LoggingHandler(LogLevel.DEBUG))
      bootstrap.childHandler(ServerInitializer())

    } finally {
      bossGroup.shutdownGracefully()
      workerGroup.shutdownGracefully()
    }
  }
}
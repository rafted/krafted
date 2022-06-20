package server

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import server.connection.Connection
import server.connection.State
import server.handler.PacketReader

class ServerInitializer : ChannelHandler {
  override fun handlerAdded(ctx: ChannelHandlerContext?) {
    ctx?.let {
      ctx.pipeline()
        .addLast(PacketReader())

      val connection = Connection(ctx.channel().id(), State.Handshake, ctx.channel())

      Server.instance.connections.add(connection)
      Server.instance.logger.info("New Connection: ${connection.id}")
    }
  }

  override fun handlerRemoved(ctx: ChannelHandlerContext?) {
    ctx?.let {
      val connection = Server.instance.connections.find { it.id == ctx.channel().id() }

      connection?.let {
        Server.instance.connections.remove(it)
        Server.instance.logger.info("Removed Connection: ${connection.id}")
      }
    }
  }

  override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
    Server.instance.logger.error("Exception caught: ${cause?.message}")
  }
}

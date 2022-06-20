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

            Server.connections.add(connection)
            Server.logger.info("New Connection: ${connection.id}")
        }
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext?) {
        ctx?.let {
            val connection = Server.connections.find { it.id == ctx.channel().id() }

            connection?.let {
                Server.connections.remove(it)
                Server.logger.info("Removed Connection: ${connection.id}")
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        Server.logger.error("Exception caught: ${cause?.message}")
    }
}

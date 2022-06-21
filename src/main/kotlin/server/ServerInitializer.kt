package server

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import server.connection.Connection
import server.connection.ConnectionEstablishedEvent
import server.connection.State
import server.handler.PacketEncoder
import server.handler.PacketReader

object ServerInitializer : ChannelHandler {
    override fun handlerAdded(ctx: ChannelHandlerContext?) {
        ctx?.let {
            ctx.pipeline()
                .addLast(
                    PacketReader(),
                    PacketEncoder()
                )

            val connection = Connection(ctx.channel().id(), State.Handshake, ctx.channel())

            Server.connections.add(connection)
            Server.logger.info("New Connection: ${connection.id}")

            Server.eventBus.post(ConnectionEstablishedEvent(connection))
        }
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext?) {
        ctx?.let {
            val connection = Server.connections.find { it.id == ctx.channel().id() }

            connection?.let {
                Server.closeConnection(connection)
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        Server.logger.error("Exception caught: ${cause?.message}")
    }
}

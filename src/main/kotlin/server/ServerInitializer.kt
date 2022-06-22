package server

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import org.tinylog.kotlin.Logger
import server.connection.Connection
import server.connection.ConnectionEstablishedEvent
import server.connection.State
import server.handler.PacketEncoder
import server.handler.PacketReader

object ServerInitializer : ChannelHandler {
    override fun handlerAdded(ctx: ChannelHandlerContext?) {
        ctx?.let {
            val pipeline = ctx.pipeline()

            pipeline.addLast(PacketReader())
            pipeline.addLast(PacketEncoder())

            val connection = Connection(ctx.channel().id(), State.Handshake, ctx.channel())

            Server.connections.add(connection)
            Logger.info("New Connection: ${connection.id}")

            Server.eventBus.post(ConnectionEstablishedEvent(connection))
        }
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext?) {
        val connection = Server.connections.find {
            it.id == ctx?.channel()?.id()
        } ?: return


        Server.closeConnection(connection)
    }


    @Deprecated("Deprecated in Java", ReplaceWith("Logger.error(\"Exception caught: \${cause?.message}\")"))
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        Logger.error("Exception caught: ${cause?.message}")
    }
}
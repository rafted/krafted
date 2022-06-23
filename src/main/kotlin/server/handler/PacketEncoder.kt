package server.handler

import event.EventBus
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import protocol.packet.Packet
import protocol.packet.PacketEvent
import protocol.varIntSize
import protocol.writeVarInt
import server.Server
import server.connection.Connection

class PacketEncoder : MessageToByteEncoder<Packet>() {
    override fun encode(ctx: ChannelHandlerContext?, msg: Packet?, out: ByteBuf?) {
        if (ctx == null || msg == null || out == null) {
            return
        }

        val connection: Connection = Server.findConnection(ctx.channel())!!

        // we'll want to call this before everything else
        // in case the event changes the packet's fields
        msg.createEvent(connection)?.let {
            EventBus.post(it)
        }

        val temp = ctx
            .alloc()
            .buffer()

        msg.pack(connection, temp)
        out.writeVarInt(temp.readableBytes() + msg.id.varIntSize() + 1)
        out.writeVarInt(msg.id)

        out.writeBytes(temp)

        temp.release()
    }
}
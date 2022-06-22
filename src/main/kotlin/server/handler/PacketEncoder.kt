package server.handler

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import protocol.packet.Packet
import protocol.varIntSize
import protocol.writeVarInt

class PacketEncoder : MessageToByteEncoder<Packet>() {

    override fun encode(ctx: ChannelHandlerContext?, msg: Packet?, out: ByteBuf?) {
        if (ctx == null || msg == null || out == null) {
            return
        }

        val temp = ctx
            .alloc()
            .buffer()

        msg.pack(temp)
        out.writeVarInt(temp.readableBytes() + msg.id.varIntSize())
        out.writeVarInt(msg.id)

        out.writeBytes(temp)

        temp.release()
    }
}
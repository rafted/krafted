package server.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protocol.packet.PacketRegistry
import protocol.readVarInt

class PacketReader : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is ByteBuf) {
            val length = msg.readVarInt()
            val id = msg.readVarInt()

            println("Packet received: $id")

            PacketRegistry.packetMap[id]?.let {
                val packet = it()

                packet.unpack(msg)

//        println(packet.fields)
            }
        }
    }
}

package server.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protocol.packet.PacketRegistry
import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.handshake.HandshakePacketEvent
import protocol.readVarInt
import server.Server

class PacketReader : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is ByteBuf) {
            val length = msg.readVarInt()
            val id = msg.readVarInt()

            PacketRegistry.packetMap[id]?.let {
                val packet = it()
                packet.unpack(msg)

                val eventBus = Server.eventBus
                if (packet is HandshakePacket) {
                    eventBus.post(HandshakePacketEvent(packet))
                }
            }
        }
    }
}

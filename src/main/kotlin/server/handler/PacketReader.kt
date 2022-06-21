package server.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protocol.packet.Direction
import protocol.packet.PacketRegistry
import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.handshake.HandshakePacketEvent
import protocol.packet.impl.status.RequestPacket
import protocol.packet.impl.status.RequestPacketEvent
import protocol.readVarInt
import server.Server
import server.connection.Connection

class PacketReader : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is ByteBuf) {
            val connection: Connection = Server.findConnection(ctx.channel())!!

            val length = msg.readVarInt()
            val id = msg.readVarInt()

            PacketRegistry.findPacket(id, connection.state, Direction.Client)?.let {
                val packet = it()
                packet.unpack(msg)

                val eventBus = Server.eventBus

                when (packet) {
                    is HandshakePacket -> eventBus.post(HandshakePacketEvent(connection, packet))
                    is RequestPacket -> eventBus.post(RequestPacketEvent(connection, packet))
                }
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        if (ctx != null) {
            Server.findConnection(ctx.channel())?.let {
                Server.closeConnection(it)
            }
        }
    }
}

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
import kotlin.experimental.and

class PacketReader : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is ByteBuf) {
            return
        }

        while (msg.isReadable) {
            val length = msg.readVarInt()
            val slice = msg.readSlice(length)

            val connection: Connection = Server.findConnection(ctx.channel())!!

            val id = slice.readVarInt()

            PacketRegistry.findPacket(id, connection.state, Direction.Client)?.let {
                val packet = it().apply {
                    unpack(slice)
                }

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

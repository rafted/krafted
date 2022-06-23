package server.handler

import event.EventBus
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protocol.packet.Direction
import protocol.packet.PacketEvent
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
            val packet = PacketRegistry.findPacket(id, connection.state, Direction.Client)?.invoke() ?: return

            packet.unpack(connection, slice)

            EventBus.post(
                PacketEvent(packet, connection)
            )

            packet.createEvent(connection)?.let {
                EventBus.post(it)
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        if (ctx != null) {
            Server.findConnection(ctx.channel())?.let {
                Server.closeConnection(it)
            }
        }
    }
}
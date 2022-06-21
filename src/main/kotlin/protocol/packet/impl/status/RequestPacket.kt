package protocol.packet.impl.status

import event.Event
import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import server.connection.Connection
import server.connection.State

class RequestPacket : Packet {
    override val id: Int = 0x00
    override val direction: Direction = Direction.Client
    override val state: State = State.Status

    override fun unpack(buffer: ByteBuf) {}
    override fun pack(buffer: ByteBuf) {}
}

data class RequestPacketEvent(val connection: Connection, val packet: RequestPacket) : Event
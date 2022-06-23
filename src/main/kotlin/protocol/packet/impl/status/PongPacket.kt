package protocol.packet.impl.status

import event.Event
import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import server.connection.Connection
import server.connection.State
import kotlin.properties.Delegates

class PongPacket : Packet {
    override val id: Int = 0x01
    override val state: State = State.Status
    override val direction: Direction = Direction.Server

    var payload by Delegates.notNull<Long>()

    override fun pack(connection: Connection, buffer: ByteBuf) {
        buffer.writeLong(payload)
    }

    override fun createEvent(connection: Connection): Event? {
        return PongPacketEvent(connection, this)
    }
}

data class PongPacketEvent(val connection: Connection, val packet: PongPacket) : Event

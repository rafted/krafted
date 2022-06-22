package protocol.packet.impl.status

import event.Event
import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import server.connection.Connection
import server.connection.State
import kotlin.properties.Delegates

class PingPacket : Packet {
    override val id: Int = 0x01
    override val state: State = State.Status
    override val direction: Direction = Direction.Client

    var payload by Delegates.notNull<Long>()

    override fun unpack(buffer: ByteBuf) {
        this.payload = buffer.readLong()
    }

    override fun createEvent(connection: Connection): Event? {
        return PingPacketEvent(connection, this)
    }
}

data class PingPacketEvent(val connection: Connection, val packet: PingPacket) : Event

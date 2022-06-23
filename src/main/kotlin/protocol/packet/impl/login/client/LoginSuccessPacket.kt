package protocol.packet.impl.login.client

import event.Event
import io.netty.buffer.ByteBuf
import protocol.*
import protocol.packet.Direction
import protocol.packet.Packet
import server.connection.Connection
import server.connection.State
import util.NameUtil
class LoginEvent(val connection: Connection, val packet: LoginSuccessPacket) : Event

class LoginSuccessPacket : Packet {
    override val id = 0x02
    override val state = State.Login
    override val direction = Direction.Server

    override fun pack(connection: Connection, buffer: ByteBuf) {
        buffer.writeString(NameUtil.nameToUUID(connection.name).toString())
        buffer.writeString(connection.name)
        buffer.writeVarInt(0)
    }

    override fun createEvent(connection: Connection): Event {
        return LoginEvent(connection, this)
    }
}
package protocol.packet.impl.handshake.serverbound

import event.Event
import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import protocol.readString
import protocol.readVarInt
import server.connection.Connection
import server.connection.State
import kotlin.properties.Delegates

class HandshakePacket : Packet {
    override val id = 0x00
    override val direction = Direction.Serverbound
    override val state = State.Handshake

    lateinit var serverAddress: String

    var protocolVersion by Delegates.notNull<Int>()
    var serverPort by Delegates.notNull<Int>()
    var nextState by Delegates.notNull<Int>()

    override fun unpack(connection: Connection, buffer: ByteBuf) {
        protocolVersion = buffer.readVarInt()
        serverAddress = buffer.readString()
        serverPort = buffer.readUnsignedShort()
        nextState = buffer.readVarInt()
    }

    override fun createEvent(connection: Connection): Event {
        return HandshakePacketEvent(connection, this)
    }
}

data class HandshakePacketEvent(val connection: Connection, val packet: HandshakePacket) : Event

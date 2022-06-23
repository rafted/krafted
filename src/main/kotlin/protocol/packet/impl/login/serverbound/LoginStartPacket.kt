package protocol.packet.impl.login.serverbound

import event.Event
import io.netty.buffer.ByteBuf
import protocol.*
import protocol.packet.Direction
import protocol.packet.Packet
import server.connection.Connection
import server.connection.State
import kotlin.properties.Delegates

class LoginStartEvent(val connection: Connection, val packet: LoginStartPacket) : Event

class LoginStartPacket : Packet {
    override val id = 0x00
    override val state = State.Login
    override val direction = Direction.Serverbound

    var sigData by Delegates.notNull<Boolean>()

    var timestamp: Long? = null

    var publicKeyLength: Int? = null
    var signatureLength: Int? = null

    var publicKey: ByteArray? = null
    var signature: ByteArray? = null

    lateinit var name: String

    override fun unpack(connection: Connection, buffer: ByteBuf) {
        name = buffer.readString()

        if (buffer.readableBytes() > 0) {
            sigData = buffer.readVarBoolean()

            if (sigData) {
                timestamp = buffer.readVarLong()

                publicKeyLength = buffer.readVarInt()
                publicKey = buffer.readBytes(publicKeyLength!!).array()

                signatureLength = buffer.readVarInt()
                signature = buffer.readBytes(signatureLength!!).array()
            }

            connection.name = name
        }
    }

    override fun createEvent(connection: Connection): Event {
        return LoginStartEvent(connection, this)
    }
}

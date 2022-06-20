package protocol.packet.impl.handshake

import io.netty.buffer.ByteBuf
import protocol.packet.Packet
import protocol.packet.Sender
import protocol.readString
import protocol.readVarInt
import server.connection.State
import kotlin.properties.Delegates

class HandshakePacket : Packet {
    override val id = 0x00
    override val sender = Sender.Client
    override val state = State.Handshake

    lateinit var serverAddress: String

    var protocolVersion by Delegates.notNull<Int>()
    var serverPort by Delegates.notNull<Int>()
    var nextState by Delegates.notNull<Int>()

    override fun unpack(buffer: ByteBuf) {
        protocolVersion = buffer.readVarInt()
        serverAddress = buffer.readString()
        serverPort = buffer.readUnsignedShort()
        nextState = buffer.readVarInt()
    }

    override fun pack(): ByteBuf {
        TODO("Not yet implemented")
    }
}

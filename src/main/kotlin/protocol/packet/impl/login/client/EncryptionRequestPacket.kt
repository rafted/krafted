package protocol.packet.impl.login.client

import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import protocol.readString
import protocol.readVarInt
import server.connection.Connection
import server.connection.State
import kotlin.properties.Delegates

class EncryptionRequestPacket : Packet {
    override val id = 0x01
    override val state = State.Login
    override val direction = Direction.Server

    override fun pack(connection: Connection, buffer: ByteBuf) {
        // TODO: 6/23/2022  
    }
}
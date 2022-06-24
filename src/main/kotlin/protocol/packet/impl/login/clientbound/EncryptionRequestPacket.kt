package protocol.packet.impl.login.clientbound

import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import server.connection.Connection
import server.connection.State

class EncryptionRequestPacket : Packet {
    override val id = 0x01
    override val state = State.Login
    override val direction = Direction.Clientbound

    override fun pack(connection: Connection, buffer: ByteBuf) {
        // TODO: 6/23/2022  
    }
}

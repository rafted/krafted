package protocol.packet.impl.login.client

import chat.ChatComponent
import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import protocol.writeChatComponent
import server.connection.Connection
import server.connection.State

class DisconnectPacket(var reason: ChatComponent? = null) : Packet {
    override val id = 0x00
    override val state = State.Login
    override val direction = Direction.Server

    fun reason(reason: ChatComponent): DisconnectPacket {
        return this.apply {
            this.reason = reason
        }
    }

    override fun pack(connection: Connection, buffer: ByteBuf) {
        reason?.let {
            buffer.writeChatComponent(it)
        }
    }
}

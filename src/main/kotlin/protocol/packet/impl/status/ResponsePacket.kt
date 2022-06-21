package protocol.packet.impl.status

import io.netty.buffer.ByteBuf
import protocol.packet.Packet
import protocol.packet.Sender
import protocol.writeString
import server.Server
import server.connection.State
import java.util.*

data class Version(val protocolVersion: Int, val serverVersion: String)
data class Player(val name: String, val id: UUID)
data class Players(val max: Int, val online: Int, val sample: List<Player>)
data class Description(val text: String)

data class Response(
    val version: Version,
    val players: Players,
    val description: Description,
    val favicon: String
)

class ResponsePacket() : Packet {
    override val id: Int = 0x00
    override val state: State = State.Status
    override val sender: Sender = Sender.Server

    lateinit var response: Response

    override fun unpack(buffer: ByteBuf) { }

    override fun pack(buffer: ByteBuf) {
        buffer.writeString(Server.gson.toJson(this.response))
    }
}

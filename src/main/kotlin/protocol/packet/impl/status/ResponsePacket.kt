package protocol.packet.impl.status

import event.Event
import io.netty.buffer.ByteBuf
import protocol.packet.Direction
import protocol.packet.Packet
import protocol.writeString
import server.Server
import server.connection.Connection
import server.connection.State
import java.util.*

data class Version(val protocolVersion: Int, val serverVersion: String)
data class Player(val name: String, val id: UUID)
data class Players(val max: Int, val online: Int, val sample: List<Player>)
data class Description(val text: String)

data class Response(
    var version: Version,
    var players: Players,
    var description: Description,
    var favicon: String
)

class ResponsePacket : Packet {
    override val id: Int = 0x00
    override val state: State = State.Status
    override val direction: Direction = Direction.Server

    lateinit var response: Response

    override fun unpack(buffer: ByteBuf) { }

    override fun pack(buffer: ByteBuf) {
        buffer.writeString(Server.gson.toJson(this.response))
    }
}

data class ResponsePacketEvent(val connection: Connection, val packet: ResponsePacket) : Event {
    var version: Version
        get() = packet.response.version
        set(value) {
            packet.response.version = value
        }

    var players: Players
        get() = packet.response.players
        set(value) {
            packet.response.players = value
        }

    var description: Description
        get() = packet.response.description
        set(value) {
            packet.response.description = value
        }

    var favicon: String
        get() = packet.response.favicon
        set(value) {
            packet.response.favicon = value
        }
}

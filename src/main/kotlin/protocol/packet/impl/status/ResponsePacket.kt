package protocol.packet.impl.status

import chat.ChatComponent
import event.Event
import io.netty.buffer.ByteBuf
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import protocol.packet.Direction
import protocol.packet.Packet
import protocol.writeString
import server.connection.Connection
import server.connection.State
import util.UUIDSerializer
import java.util.*

typealias ResponsePacketEvent = Response

@Serializable
data class Version(
    var protocol: Int,
    var name: String
)

@Serializable
data class Player(
    var name: String,
    @Serializable(with = UUIDSerializer::class)
    var id: UUID
)

@Serializable
data class Players(
    var max: Int,
    var online: Int,
    var sample: List<Player>
)

@Serializable
data class Response(
    var version: Version,
    var players: Players,
    var description: ChatComponent,
    var favicon: String
) : Event

class ResponsePacket : Packet {
    override val id: Int = 0x00
    override val state: State = State.Status
    override val direction: Direction = Direction.Server

    lateinit var response: Response

    override fun pack(connection: Connection, buffer: ByteBuf) {
        buffer.writeString(
            Json.encodeToString(this.response)
        )
    }

    override fun createEvent(connection: Connection): Event {
        return this.response
    }
}

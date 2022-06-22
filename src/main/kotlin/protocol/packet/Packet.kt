package protocol.packet

import event.Event
import io.netty.buffer.ByteBuf
import server.connection.Connection
import server.connection.State

interface Packet {
    val id: Int
    val state: State
    val direction: Direction

    fun unpack(buffer: ByteBuf) {}
    fun pack(buffer: ByteBuf) {}

    fun createEvent(connection: Connection): Event? {
        return null
    }
}

class PacketEvent(val packet: Packet, val connection: Connection) : Event

/*
class PacketBuilder {

    private var id: Int = 0
    private var sender: Sender = Sender.SERVER
    private var state: State = State.HANDSHAKE

    private lateinit var unpack_f: (buf: ByteBuf) -> Map<String, Any>
    private lateinit var pack_f: (Map<String, Any>) -> ByteBuf

    fun id(id: Int) {
        this.id = id
    }

    fun sender(sender: Sender) {
        this.sender = sender
    }

    fun state(state: State) {
        this.state = state
    }

    fun unpack(f: (buf: ByteBuf) -> Map<String, Any>) {
        this.unpack_f = f
    }

    fun pack(f: (Map<String, Any>) -> ByteBuf) {
        this.pack_f = f
    }

    fun build(): Packet {
        return object : Packet {
            override val id: Int
                get() = this.id
            override val sender: Sender
                get() = this.sender
            override val state: State
                get() = this.state
            override var fields: Map<String, Any> = mutableMapOf()

            override fun unpack(data: ByteBuf) = run {
                this.fields = unpack_f(data)
            }

            override fun pack(): ByteBuf {
                return pack_f(this.fields)
            }
        }
    }

}

fun createPacket(init: PacketBuilder.() -> Unit) : Packet {
    val builder = PacketBuilder()
    builder.init()
    return builder.build()
}
 */

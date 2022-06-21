package protocol.packet

import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.status.RequestPacket
import server.connection.State

object PacketRegistry {
    val packets = mapOf(
        PacketId(0, State.Handshake, Sender.Client) to { HandshakePacket() },
        PacketId(0, State.Status, Sender.Client) to { RequestPacket() },
    )

    fun findPacket(id: Int, state: State, sender: Sender): (() -> Packet)? {
        return packets[PacketId(id, state, sender)]
    }
}

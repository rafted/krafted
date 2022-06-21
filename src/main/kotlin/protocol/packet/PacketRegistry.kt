package protocol.packet

import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.status.RequestPacket
import server.connection.State

object PacketRegistry {

    val packets: Map<Sender, Map<State, Map<Int, () -> Packet>>> = mapOf(
        Sender.Client to mapOf(
            State.Handshake to mapOf(
                0 to { HandshakePacket() },
            ),
            State.Status to mapOf(
                0 to { RequestPacket() },
            ),
        ))

    fun findPacket(id: Int, state: State, sender: Sender): (() -> Packet)? {
        return packets[sender]?.get(state)?.get(id)
    }

}

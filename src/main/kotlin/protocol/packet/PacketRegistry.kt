package protocol.packet

import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.status.RequestPacket
import protocol.packet.impl.status.ResponsePacket
import server.connection.State

object PacketRegistry {

    val packets: Map<Direction, Map<State, Map<Int, () -> Packet>>> = mapOf(
        Direction.Client to mapOf(
            State.Handshake to mapOf(
                0 to {
                    HandshakePacket()
                },
            ),
            State.Status to mapOf(
                0 to {
                    RequestPacket()
                },
            ),
        ),
        Direction.Server to mapOf(
            State.Status to mapOf(
                0 to { ResponsePacket() },
            ),
        ),
    )

    fun findPacket(id: Int, state: State, direction: Direction): (() -> Packet)? {
        return packets[direction]?.get(state)?.get(id)
    }
}

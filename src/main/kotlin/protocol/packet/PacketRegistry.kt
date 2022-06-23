package protocol.packet

import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.login.client.DisconnectPacket
import protocol.packet.impl.login.client.EncryptionRequestPacket
import protocol.packet.impl.login.client.LoginSuccessPacket
import protocol.packet.impl.login.server.LoginStartPacket
import protocol.packet.impl.status.PingPacket
import protocol.packet.impl.status.PongPacket
import protocol.packet.impl.status.RequestPacket
import protocol.packet.impl.status.ResponsePacket
import server.connection.State

object PacketRegistry {

    val packets: Map<Direction, Map<State, Map<Int, () -> Packet>>> = mapOf(
        Direction.Client to mapOf(
            State.Handshake to mapOf(
                0x00 to { HandshakePacket() },
            ),
            State.Status to mapOf(
                0x00 to { RequestPacket() },
                0x01 to { PingPacket() }
            ),
            State.Login to mapOf(
                0x00 to { LoginStartPacket() }
            )
        ),
        Direction.Server to mapOf(
            State.Status to mapOf(
                0x00 to { ResponsePacket() },
                0x01 to { PongPacket() }
            ),
            State.Login to mapOf(
                0x00 to { DisconnectPacket() },
                0x01 to { EncryptionRequestPacket() },
                0x02 to { LoginSuccessPacket() }
            )
        ),
    )

    fun findPacket(id: Int, state: State, direction: Direction): (() -> Packet)? {
        return packets[direction]
            ?.get(state)
            ?.get(id)
    }
}

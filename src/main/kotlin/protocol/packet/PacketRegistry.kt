package protocol.packet

import protocol.packet.impl.handshake.HandshakePacket

object PacketRegistry {
    val packetMap: Map<Int, () -> Packet> = mapOf(
        0x00 to {
            HandshakePacket()
        }
    )
}

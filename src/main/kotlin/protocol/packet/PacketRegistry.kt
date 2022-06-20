package protocol.packet

import protocol.packet.impl.handshake.HandshakePacket

object PacketRegistry {
    val packetMap = mapOf(
        0x00 to {
            HandshakePacket()
        }
    )
}
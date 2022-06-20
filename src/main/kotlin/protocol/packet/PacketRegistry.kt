package protocol.packet

import protocol.packet.impl.HandshakePackets

object PacketRegistry {
    private val packetMap = mapOf(
      0x00 to HandshakePackets.Handshake
    )
}
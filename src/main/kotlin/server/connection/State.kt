package server.connection

import protocol.packet.Packet
import protocol.packet.impl.handshake.HandshakePacket

enum class State(val packets: Map<Int, () -> Packet>) {
    Handshake(mapOf(0x00 to {HandshakePacket()})),
    Status(mapOf()),
    Login(mapOf()),
    Play(mapOf()),
    Closed(mapOf())
}

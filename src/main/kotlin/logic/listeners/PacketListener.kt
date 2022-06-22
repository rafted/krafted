package logic.listeners

import event.BusListener
import event.EventBus
import event.Listen
import protocol.packet.PacketEvent
import protocol.packet.impl.handshake.HandshakePacket
import protocol.packet.impl.handshake.HandshakePacketEvent
import protocol.packet.impl.status.RequestPacket
import protocol.packet.impl.status.RequestPacketEvent

object PacketListener : BusListener {
    @Listen
    fun onPacket(event: PacketEvent) {
        val packet = event.packet
        val connection = event.connection

        when (packet) {
            is HandshakePacket -> EventBus.post(HandshakePacketEvent(connection, packet))
            is RequestPacket -> EventBus.post(RequestPacketEvent(connection, packet))
        }
    }
}
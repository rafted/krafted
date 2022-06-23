package logic.listeners

import event.BusListener
import event.Listen
import protocol.packet.impl.status.PingPacketEvent
import protocol.packet.impl.status.clientbound.PongPacket
import protocol.packet.impl.status.clientbound.PongPacketEvent

object PingListener : BusListener {

    @Listen
    fun onPing(event: PingPacketEvent) {
        val pong = PongPacket()

        pong.payload = event.packet.payload
        event.connection.send(pong)
    }

    @Listen
    fun onPong(event: PongPacketEvent) {
    }
}

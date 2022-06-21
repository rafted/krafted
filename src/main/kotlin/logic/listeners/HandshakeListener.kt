package logic.listeners

import event.Handle
import event.Listener
import protocol.packet.impl.handshake.HandshakePacketEvent

class HandshakeListener : Listener {

    @Handle(HandshakePacketEvent::class)
    fun onHandshake(event: HandshakePacketEvent) {
    }
}

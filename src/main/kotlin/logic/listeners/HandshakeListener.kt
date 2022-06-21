package logic.listeners

import event.Handle
import event.Listener
import protocol.packet.impl.handshake.HandshakePacketEvent
import server.connection.State

class HandshakeListener : Listener {

    @Handle(HandshakePacketEvent::class)
    fun onHandshake(event: HandshakePacketEvent) {
        var nextState = if (event.packet.nextState == 1) {
            State.Status
        } else {
            State.Login
        }

        event.connection.state = nextState
    }
}

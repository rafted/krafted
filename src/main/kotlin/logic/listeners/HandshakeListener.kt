package logic.listeners

import event.Handle
import event.Listener
import protocol.packet.impl.handshake.HandshakePacketEvent
import protocol.packet.impl.status.RequestPacketEvent
import server.connection.State

object HandshakeListener : Listener {

    @Handle(HandshakePacketEvent::class)
    fun onHandshake(event: HandshakePacketEvent) {
        val nextState = if (event.packet.nextState == 1) {
            State.Status
        } else {
            State.Login
        }

        event.connection.state = nextState
    }

    @Handle(RequestPacketEvent::class)
    fun onRequest(event: RequestPacketEvent) {
        if (event.connection.state == State.Status) {
            TODO("reply back with status...")
        }
    }
}

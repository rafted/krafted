package logic.listeners

import event.Listener
import protocol.packet.impl.handshake.HandshakePacketEvent
import protocol.packet.impl.status.RequestPacketEvent
import protocol.packet.impl.status.ResponsePacket
import server.Server
import server.connection.State

object HandshakeListener {

    init {
        Server.eventBus.subscribe<HandshakePacketEvent> {
            val nextState = if (it.packet.nextState == 1) {
                State.Status
            } else {
                State.Login
            }

            event.connection.state = nextState
        }
    }

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
            val packet = ResponsePacket()
            packet.response = Server.makeStatusRespose()

            event.connection.send(packet)
        }
    }
}

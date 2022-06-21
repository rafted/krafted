package logic.listeners

import event.BusHandle
import event.BusListener
import event.Listener
import protocol.packet.impl.handshake.HandshakePacketEvent
import protocol.packet.impl.status.RequestPacketEvent
import protocol.packet.impl.status.ResponsePacket
import server.Server
import server.connection.State

object HandshakeListener : BusListener {

    init {
        Server.eventBus.subscribe<HandshakePacketEvent> {
            val nextState = if (it.packet.nextState == 1) {
                State.Status
            } else {
                State.Login
            }

            it.connection.state = nextState
        }
    }

    @BusHandle
    fun onHandshake(event: HandshakePacketEvent) {
        val nextState = if (event.packet.nextState == 1) {
            State.Status
        } else {
            State.Login
        }

        event.connection.state = nextState
    }

    @BusHandle
    fun onRequest(event: RequestPacketEvent) {
        if (event.connection.state == State.Status) {
            val packet = ResponsePacket()
//            packet.response = Server.makeStatusRespose()

            event.connection.send(packet)
        }
    }
}

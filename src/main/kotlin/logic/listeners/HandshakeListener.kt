package logic.listeners

import event.BusListener
import event.Listen
import protocol.packet.impl.handshake.serverbound.HandshakePacketEvent
import protocol.packet.impl.status.clientbound.ResponsePacket
import protocol.packet.impl.status.serverbound.RequestPacketEvent
import server.Server
import server.connection.State

object HandshakeListener : BusListener {

    @Listen
    fun onHandshake(event: HandshakePacketEvent) {
        val nextState = if (event.packet.nextState == 1) {
            State.Status
        } else {
            State.Login
        }

        event.connection.state = nextState
    }

    @Listen
    fun onRequest(event: RequestPacketEvent) {
        if (event.connection.state == State.Status) {
            val packet = ResponsePacket()

            packet.response = Server.makeStatusRespose()
            event.connection.send(packet)
        }
    }
}

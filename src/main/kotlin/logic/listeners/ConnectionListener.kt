package logic.listeners

import event.BusListener
import event.Listen
import protocol.packet.impl.status.ResponsePacketEvent
import server.connection.ConnectionClosedEvent
import server.connection.ConnectionEstablishedEvent

object ConnectionListener : BusListener {

    // TODO: 6/22/2022 remove, this is simply for testing
    @Listen
    fun onResponsePacket(event: ResponsePacketEvent) {
        event.players.max = 69
        event.description.text = "hey"
    }

    @Listen
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) {
    }

    @Listen
    fun onConnectionClosed(event: ConnectionClosedEvent) {
    }
}

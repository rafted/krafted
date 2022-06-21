package logic.listeners

import event.BusListener
import event.Listen
import server.connection.ConnectionClosedEvent
import server.connection.ConnectionEstablishedEvent

object ConnectionListener : BusListener {

    @Listen
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) {
    }

    @Listen
    fun onConnectionClosed(event: ConnectionClosedEvent) {
    }
}

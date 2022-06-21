package logic.listeners

import event.Listen
import event.BusListener
import server.connection.ConnectionClosedEvent
import server.connection.ConnectionEstablishedEvent

object ConnectionListener : BusListener {

    @Listen
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) { }

    @Listen
    fun onConnectionClosed(event: ConnectionClosedEvent) { }
}

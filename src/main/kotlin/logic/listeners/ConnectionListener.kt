package logic.listeners

import event.BusHandle
import event.BusListener
import server.connection.ConnectionClosedEvent
import server.connection.ConnectionEstablishedEvent

object ConnectionListener : BusListener {

    @BusHandle
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) { }

    @BusHandle
    fun onConnectionClosed(event: ConnectionClosedEvent) { }
}

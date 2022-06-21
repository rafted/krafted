package logic.listeners

import event.Handle
import event.Listener
import server.connection.event.ConnectionClosedEvent
import server.connection.event.ConnectionEstablishedEvent

class ConnectionListener : Listener {

    @Handle(ConnectionEstablishedEvent::class)
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) {
        println("Connection established")
    }

    @Handle(ConnectionClosedEvent::class)
    fun onConnectionClosed(event: ConnectionClosedEvent) {
        println("Connection closed")
    }

}
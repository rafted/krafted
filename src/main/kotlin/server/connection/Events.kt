package server.connection.event

import event.Event
import server.connection.Connection

class ConnectionEstablishedEvent(val connection: Connection) : Event
class ConnectionClosedEvent(val connection: Connection) : Event

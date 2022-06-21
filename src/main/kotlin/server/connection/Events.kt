package server.connection

import event.Event

class ConnectionEstablishedEvent(val connection: Connection) : Event
class ConnectionClosedEvent(val connection: Connection) : Event

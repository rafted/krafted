package logic.listeners

import event.BusListener
import event.Listen
import protocol.packet.impl.login.client.LoginEvent
import protocol.packet.impl.login.client.LoginSuccessPacket
import protocol.packet.impl.login.server.LoginStartEvent
import server.connection.State

object LoginListener : BusListener {
    @Listen
    fun onLoginSuccess(event: LoginEvent) {
        event.connection.state = State.Play
    }

    @Listen
    fun onLoginStart(event: LoginStartEvent) {
        event.connection.send(
            LoginSuccessPacket()
        )
    }
}

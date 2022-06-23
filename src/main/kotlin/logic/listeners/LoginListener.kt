package logic.listeners

import event.BusListener
import event.Listen
import protocol.packet.impl.login.clientbound.LoginEvent
import protocol.packet.impl.login.clientbound.LoginSuccessPacket
import protocol.packet.impl.login.serverbound.LoginStartEvent
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

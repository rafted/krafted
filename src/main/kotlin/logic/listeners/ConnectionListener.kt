package logic.listeners

import chat.ChatComponent
import chat.color.ChatColor
import event.BusListener
import event.Listen
import protocol.packet.impl.status.ResponsePacketEvent
import server.connection.ConnectionClosedEvent
import server.connection.ConnectionEstablishedEvent
import java.awt.Color

object ConnectionListener : BusListener {

    // TODO: 6/22/2022 remove, this is simply for testing
    @Listen
    fun onResponsePacket(event: ResponsePacketEvent) {
        event.players.max = 69
        event.description = ChatComponent()
            .text("hey")
            .bold(true)
            .color(ChatColor.CYAN)
            .child {
                it.text(" child")
                    .color(ChatColor.RED)
                    .bold(false)
            }
            .child {
                it.text(" second child")
                    .color(ChatColor.PINK)
                    .bold(false)
            }
    }

    @Listen
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) {
    }

    @Listen
    fun onConnectionClosed(event: ConnectionClosedEvent) {
    }
}

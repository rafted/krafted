package logic.listeners

import chat.ChatComponent
import chat.color.ChatColor
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
        event.description = ChatComponent()
            .text("                Hypixel Network")
            .bold(false)
            .color(ChatColor.LIGHT_GREEN)
            .child {
                it.text(" [1.8-1.19]")
                    .color(ChatColor.RED)
                    .bold(false)
            }
            .child {
                it.text("\n          SKYBLOCK, BED WARS + MORE")
                    .color(ChatColor.YELLOW)
                    .bold(true)
            }
    }

    @Listen
    fun onConnectionEstablished(event: ConnectionEstablishedEvent) {
    }

    @Listen
    fun onConnectionClosed(event: ConnectionClosedEvent) {
    }
}

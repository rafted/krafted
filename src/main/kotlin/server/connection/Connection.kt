package server.connection

import io.netty.channel.Channel
import io.netty.channel.ChannelId
import protocol.packet.Packet

data class Connection(
    val id: ChannelId,
    var state: State,
    val channel: Channel
) {
    lateinit var uuid: String
    lateinit var username: String

    fun send(packet: Packet) {
        channel.writeAndFlush(packet)
    }
}

package server.connection

import io.netty.channel.Channel
import io.netty.channel.ChannelId
import protocol.packet.Packet

data class Connection(
    val id: ChannelId,
    var state: State,
    val channel: Channel
) {
    fun send(packet: Packet) {
        println(packet)
        channel.write(packet)
        channel.flush()
    }
}

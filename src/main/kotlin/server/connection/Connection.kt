package server.connection

import io.netty.channel.Channel
import io.netty.channel.ChannelId

data class Connection(
    val id: ChannelId,
    val state: State,
    val channel: Channel
)
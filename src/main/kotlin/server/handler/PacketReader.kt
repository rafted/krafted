package server.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class PacketReader : ChannelInboundHandlerAdapter() {

  override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
    if (msg !is ByteBuf) {
      return
    }
  }

}

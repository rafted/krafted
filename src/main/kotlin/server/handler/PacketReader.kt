package server.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protocol.packet.Packet
import protocol.packet.PacketRegistry
import protocol.packet.impl.handshake.HandshakePacket
import protocol.readVarInt
import kotlin.reflect.KFunction
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

class PacketReader : ChannelInboundHandlerAdapter() {

  override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
    if (msg is ByteBuf) {
      val length = msg.readVarInt()
      val id = msg.readVarInt()

      println("Packet received: $id")

      PacketRegistry.packetMap[id]?.let {
        val packet = it.constructors.first().newInstance() as Packet

        packet.unpack(msg)

        println(packet.fields)
      }
    }
  }

}

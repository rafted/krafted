package protocol.packet.impl.handshake

import io.netty.buffer.ByteBuf
import protocol.packet.Packet
import protocol.packet.Sender
import protocol.readString
import protocol.readVarInt
import server.connection.State

class HandshakePacket : Packet {
  override val id = 0x00
  override val sender = Sender.CLIENT
  override val state = State.HANDSHAKE
  override var fields: Map<String, Any> = mutableMapOf()

  override fun unpack(buffer: ByteBuf) {
    this.fields = mapOf(
      "protocolVersion" to buffer.readVarInt(),
      "serverAddress" to buffer.readString(),
      "serverPort" to buffer.readUnsignedShort(),
      "nextState" to buffer.readVarInt()
    )
  }

  override fun pack(): ByteBuf {
    TODO("Not yet implemented")
  }
}
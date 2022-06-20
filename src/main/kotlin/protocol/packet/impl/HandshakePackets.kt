package protocol.packet.impl

import protocol.packet.Sender
import protocol.packet.createPacket
import protocol.readString
import protocol.readVarInt
import server.connection.State

object HandshakePackets {

  val Handshake = createPacket {
    id(0x00)
    sender(Sender.CLIENT)
    state(State.HANDSHAKE)
    unpack {
      return@unpack mapOf(
        "protocolVersion" to it.readVarInt(),
        "serverAddress" to it.readString(),
        "port" to it.readUnsignedShort(),
        "nextState" to it.readVarInt()
      )
    }
  }

}


package protocol.packet.impl

import protocol.Datatypes.readVarInt
import protocol.packet.Sender
import protocol.packet.createPacket
import server.connection.State

object HandshakePackets {

  val Handshake = createPacket {
    id(0x00)
    sender(Sender.CLIENT)
    state(State.HANDSHAKE)
    unpack {
      return@unpack mapOf(
        "protocolVersion" to it.readVarInt(),
    //          "serverAddress" to buf.readString(),
        "port" to it.readUnsignedShort(),
        "nextState" to it.readVarInt()
      )
    }
  }

}
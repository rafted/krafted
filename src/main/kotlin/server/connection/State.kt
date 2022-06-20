package server.connection

enum class State {

  HANDSHAKE,
  STATUS,
  LOGIN,
  PLAY,
  CLOSED

}

package server.connection

enum class State {
  Handshake,
  Status,
  Login,
  Play,
  Closed
}

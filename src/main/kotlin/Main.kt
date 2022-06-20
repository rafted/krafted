import server.Server
import server.ServerConfig

fun main(args: Array<String>) {
  val server = Server(ServerConfig("0.0.0.0", 25565))
  server.start()
}
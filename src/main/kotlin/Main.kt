import server.Server
import server.ServerConfig

fun main(args: Array<String>)
{
    val port = (args.getOrNull(0) ?: "25565")
        .toIntOrNull() ?: 25565

    Server.start(
        ServerConfig("0.0.0.0", port)
    )
}

fun <T> Array<T>.getOrNull(index: Int): T?
{
    return try
    {
        this[index]
    } catch (ignored: IndexOutOfBoundsException)
    {
        null
    }
}
import kotlinx.serialization.json.Json
import server.Server

fun main(args: Array<String>) {
    Server.start(
        Json {
            isLenient = false
            prettyPrint = true
            coerceInputValues = true
            encodeDefaults = true
        }
    )
}

fun <T> Array<T>.getOrNull(index: Int): T? {
    return try {
        this[index]
    } catch (ignored: IndexOutOfBoundsException) {
        null
    }
}

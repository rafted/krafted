package chat

import kotlinx.serialization.Serializable
import util.ColorSerializer

// TODO: 6/22/2022 finish. it's 4 am. we should also 100% distribute these across some classes, instead of in this singular kotlin file.
@Serializable
data class ChatComponent(
    val text: String,
    val bold: Boolean,
    val color: @Serializable(with = ColorSerializer::class) ChatColor,
    val siblings: List<ChatComponent>,
)

@Serializable
data class ChatColor(
    val code: Char,
    val commonName: String,
    val id: String,
    val rgb: Int,
    val vanilla: Boolean = false
) {
    @JvmOverloads
    constructor(
        code: Char,
        commonName: String,
        id: String,
        red: Short,
        green: Short,
        blue: Short,
        vanilla: Boolean = false
    ) : this(code, commonName, id, ((red shl 16) or (green shl 8) or blue.toInt()), vanilla)

    companion object {
        fun fromMap(map: Map<String, String>): ChatColor {
            return ChatColor(
                map["code"]!!.toCharArray()[0],
                map["commonName"]!!,
                map["id"]!!,
                map["rgb"]!!.toInt(),
                map["vanilla"]!!.toBoolean()
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "code" to code,
            "commonName" to commonName,
            "id" to id,
            "rgb" to rgb,
            "vanilla" to vanilla
        )
    }
}


val BLACK = ChatColor('0', "Black", "black", 0, 0, 0, true)
val DARK_BLUE = ChatColor('1', "Dark blue", "dark_blue", 0, 0, 170, true)
val DARK_GREEN = ChatColor('2', "Dark green", "dark_green", 0, 170, 0, true)
val DARK_CYAN = ChatColor('3', "Dark cyan", "dark_aqua", 0, 170, 170, true)
val DARK_RED = ChatColor('4', "Dark red", "dark_red", 170, 0, 0, true)
val PURPLE = ChatColor('5', "Purple", "dark_purple", 170, 0, 170, true)
val GOLD = ChatColor('6', "Gold", "gold", 255, 170, 0, true)
val GRAY = ChatColor('7', "Gray", "gray", 170, 170, 170, true)
val DARK_GRAY = ChatColor('8', "Dark gray", "dark_gray", 85, 85, 85, true)
val BLUE = ChatColor('9', "Blue", "blue", 85, 85, 255, true)
val LIGHT_GREEN = ChatColor('a', "Bright green", "light_green", 85, 255, 85, true)
val CYAN = ChatColor('b', "Cyan", "cyan", 85, 255, 255, true)
val RED = ChatColor('c', "Red", "red", 255, 85, 85, true)
val PINK = ChatColor('d', "Pink", "light_purple", 255, 85, 255, true)
val YELLOW = ChatColor('e', "Yellow", "yellow", 255, 255, 85, true)
val WHITE = ChatColor('f', "White", "white", 255, 255, 255, true)

fun mapId(color: ChatColor): Pair<String, ChatColor> {
    return color.id to color
}

fun mapCode(color: ChatColor): Pair<Char, ChatColor> {
    return color.code to color
}

val COLOR_ID_MAP = hashMapOf(
    mapId(BLACK),
    mapId(DARK_BLUE),
    mapId(DARK_GREEN),
    mapId(DARK_CYAN),
    mapId(DARK_RED),
    mapId(PURPLE),
    mapId(GOLD),
    mapId(GRAY),
    mapId(DARK_GRAY),
    mapId(BLUE),
    mapId(LIGHT_GREEN),
    mapId(CYAN),
    mapId(RED),
    mapId(PINK),
    mapId(YELLOW),
    mapId(WHITE),
)

val COLOR_CODE_MAP = hashMapOf(
    mapCode(BLACK),
    mapCode(DARK_BLUE),
    mapCode(DARK_GREEN),
    mapCode(DARK_CYAN),
    mapCode(DARK_RED),
    mapCode(PURPLE),
    mapCode(GOLD),
    mapCode(GRAY),
    mapCode(DARK_GRAY),
    mapCode(BLUE),
    mapCode(LIGHT_GREEN),
    mapCode(CYAN),
    mapCode(RED),
    mapCode(PINK),
    mapCode(YELLOW),
    mapCode(WHITE),
)

private infix fun Short.shl(i: Int): Int {
    return this.toInt().shl(i)
}

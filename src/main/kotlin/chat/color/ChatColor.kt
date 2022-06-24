package chat.color

import kotlinx.serialization.Serializable

@Serializable
data class ChatColor(
    val code: Char? = null,
    val commonName: String? = null,
    val id: String? = null,
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
        val LIGHT_GREEN =
            ChatColor('a', "Bright green", "light_green", 85, 255, 85, true) // TODO: 6/22/2022 appears as grey
        val CYAN = ChatColor('b', "Cyan", "aqua", 85, 255, 255, true)
        val RED = ChatColor('c', "Red", "red", 255, 85, 85, true)
        val PINK = ChatColor('d', "Pink", "light_purple", 255, 85, 255, true)
        val YELLOW = ChatColor('e', "Yellow", "yellow", 255, 255, 85, true)
        val WHITE = ChatColor('f', "White", "white", 255, 255, 255, true)
    }
}

private infix fun Short.shl(i: Int): Int {
    return this.toInt().shl(i)
}
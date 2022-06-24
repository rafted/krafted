package chat.color

object ColorMappings {
    private fun mapId(
        color: ChatColor
    ): Pair<String, ChatColor> {
        return (color.id ?: throw IllegalArgumentException("Provided color is not a vanilla color")) to color
    }

    private fun mapCode(
        color: ChatColor
    ): Pair<Char, ChatColor> {
        return (color.code ?: throw IllegalArgumentException("Provided color is not a vanilla color")) to color
    }

    private val COLOR_ID_MAP = hashMapOf(
        mapId(ChatColor.BLACK),
        mapId(ChatColor.DARK_BLUE),
        mapId(ChatColor.DARK_GREEN),
        mapId(ChatColor.DARK_CYAN),
        mapId(ChatColor.DARK_RED),
        mapId(ChatColor.PURPLE),
        mapId(ChatColor.GOLD),
        mapId(ChatColor.GRAY),
        mapId(ChatColor.DARK_GRAY),
        mapId(ChatColor.BLUE),
        mapId(ChatColor.LIGHT_GREEN),
        mapId(ChatColor.CYAN),
        mapId(ChatColor.RED),
        mapId(ChatColor.PINK),
        mapId(ChatColor.YELLOW),
        mapId(ChatColor.WHITE),
    )

    private val COLOR_CODE_MAP = hashMapOf(
        mapCode(ChatColor.BLACK),
        mapCode(ChatColor.DARK_BLUE),
        mapCode(ChatColor.DARK_GREEN),
        mapCode(ChatColor.DARK_CYAN),
        mapCode(ChatColor.DARK_RED),
        mapCode(ChatColor.PURPLE),
        mapCode(ChatColor.GOLD),
        mapCode(ChatColor.GRAY),
        mapCode(ChatColor.DARK_GRAY),
        mapCode(ChatColor.BLUE),
        mapCode(ChatColor.LIGHT_GREEN),
        mapCode(ChatColor.CYAN),
        mapCode(ChatColor.RED),
        mapCode(ChatColor.PINK),
        mapCode(ChatColor.YELLOW),
        mapCode(ChatColor.WHITE),
    )

    fun colorById(id: String): ChatColor? {
        return COLOR_ID_MAP[id]
    }

    fun colorByChar(char: Char): ChatColor? {
        return COLOR_CODE_MAP[char]
    }
}
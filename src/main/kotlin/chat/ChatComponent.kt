package chat

import chat.color.ChatColor
import kotlinx.serialization.Serializable
import util.ColorSerializer

@Serializable
data class ChatComponent(
    var text: String = "",
    var bold: Boolean = false,
    var color: @Serializable(with = ColorSerializer::class) ChatColor = ChatColor.WHITE,
    val extra: MutableList<ChatComponent> = mutableListOf(),
) {
    fun text(text: String): ChatComponent {
        this.text = text
        return this
    }

    fun bold(bold: Boolean): ChatComponent {
        this.bold = bold
        return this
    }

    fun color(color: ChatColor): ChatComponent {
        this.color = color
        return this
    }

    fun child(builder: (ChatComponent) -> ChatComponent): ChatComponent {
        this.extra.add(builder.invoke(ChatComponent()))
        return this
    }
}
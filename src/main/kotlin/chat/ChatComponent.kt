package chat

import chat.color.ChatColor
import chat.color.ColorMappings
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color

@Serializable
data class ChatComponent(
    var text: String,
    var bold: Boolean,
    var color: @Serializable(with = ColorSerializer::class) ChatColor = ChatColor.WHITE,
    val extra: MutableList<ChatComponent> = mutableListOf(),
) {
    constructor() : this("", false)

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

private object ColorSerializer : KSerializer<ChatColor> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ChatColor {
        val text = decoder.decodeString()

        return ColorMappings.colorById(text) ?: ChatColor(
            ' ', "", "", Color.decode(text).rgb
        )
    }

    override fun serialize(encoder: Encoder, value: ChatColor) {
        encoder.encodeString(
            if (value.vanilla) {
                value.id
            } else {
                Integer.toHexString(value.rgb)
            }
        )
    }
}

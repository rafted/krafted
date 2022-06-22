package util

import chat.COLOR_ID_MAP
import chat.ChatColor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

object ColorSerializer : KSerializer<ChatColor> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ChatColor {
        val text = decoder.decodeString()

        return COLOR_ID_MAP[text] ?: ChatColor(
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
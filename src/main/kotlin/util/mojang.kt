package util

import kong.unirest.Unirest
import kong.unirest.json.JSONObject
import java.util.*

// TODO: 6/23/2022 change this to not use Unirest, just simply copy-pasted this from my old code.
object NameUtil {
    private const val MOJANG_ENDPOINT = "https://api.mojang.com/"

    fun nameToUUID(name: String): UUID {
        val response = Unirest
            .get("$MOJANG_ENDPOINT/users/profiles/minecraft/$name")
            .asJson()

        val body = response.body

        return UUID.fromString(body.`object`.getString("id").insertHyphens())
    }

    fun getNameHistory(identifier: UUID): Collection<Name> {
        val response = Unirest
            .get("$MOJANG_ENDPOINT/user/profiles/$identifier/names")
            .asJson()

        val body = response.body
        val names = mutableListOf<Name>()

        body.array.forEach {
            val json = it

            if (json is JSONObject) {
                names += Name(
                    json.getOrNull<String>("name") ?: "name not found",
                    json.getOrNull<Long>("changedToAt") ?: 0
                )
            }
        }

        return names
    }

    private fun String.insertHyphens(): String {
        return StringBuffer(this).apply {
            listOf(
                20,
                16,
                12,
                8
            ).forEach {
                this.insert(it, '-')
            }
        }.toString()
    }
}

class Name(val name: String, val date: Long)
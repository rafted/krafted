package util

import kong.unirest.json.JSONObject

inline fun <reified T> JSONObject.getOrNull(
    key: String
): T? {
    // could check with if (this.has(key)) instead of catching the exception,
    // but this allows us to return null not just if the field is not in the object.
    // it will also return null if any other exceptions occur.
    return try {
        this.get(key) as T
    } catch (ignored: Exception) {
        null
    }
}
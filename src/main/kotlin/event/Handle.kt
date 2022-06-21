package event

import java.lang.reflect.Method
import kotlin.reflect.KClass

annotation class Handle(
    val value: KClass<out Event> = Event::class
)

data class HandleData(
    val parent: Any,
    val method: Method,
    val type: Class<out Any>,
)

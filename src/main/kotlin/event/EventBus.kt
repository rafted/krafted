package event

import org.tinylog.kotlin.Logger
import protocol.packet.PacketEvent

interface Event

data class Listener<T : Event>(
    val type: Class<T>,
    val action: (T) -> Unit
) {
    fun process(event: Event) {
        action(event as T)
    }
}

object EventBus {
    private val handlers = mutableListOf<Listener<*>>()

    fun subscribe(listener: BusListener) {
        for (method in listener.javaClass.declaredMethods) {
            if (!method.isAnnotationPresent(Listen::class.java)) {
                continue
            }

            val type = method.parameterTypes.getOrNull(0) ?: continue

            subscribe(
                type.asSubclass(Event::class.java)
            ) {
                method.invoke(listener, it)
            }
        }
    }

    inline fun <reified T : Event> subscribe(noinline action: (T) -> Unit) {
        subscribe(T::class.java) {
            action.invoke(it)
        }
    }

    fun <T : Event> subscribe(type: Class<T>, action: (T) -> Unit) {
        val listener = Listener(type, action)

        handlers.add(listener)
    }

    fun <T : Event> post(event: T) {
        if (event !is PacketEvent) {
            Logger.debug(event.javaClass.simpleName + " posted")
        }

        this.handlers
            .filter { it.type == event::class.java }
            .forEach {
                it.process(event)
            }
    }
}

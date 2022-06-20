package event

interface EventBus {
    val listeners: Map<Listener, List<HandleData>>

    fun post(event: Event)
    fun subscribe(listener: Listener)
}

class EventBusImpl : EventBus {
    override val listeners = hashMapOf<Listener, MutableList<HandleData>>()

    override fun post(event: Event) {
        for (listener in listeners) {
            val data = listener.value

            data
                .filter { it.javaClass == event.javaClass }
                .forEach {
                    it.method.invoke(it.parent, event)
                }
        }
    }

    override fun subscribe(listener: Listener) {
        this.listeners.putIfAbsent(listener, mutableListOf())

        listener.javaClass.declaredMethods
            .filter { it.isAnnotationPresent(Handle::class.java) }
            .forEach {
                val handle = it.getAnnotation(Handle::class.java)

                val type = if (handle.value == Event::class.java) {
                    it.parameterTypes[0]
                } else {
                    handle.value.java
                }

                listeners[listener]!!.add(
                    HandleData(listener, it, type)
                )
            }
    }
}
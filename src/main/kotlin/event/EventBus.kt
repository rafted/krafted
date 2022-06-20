package event

interface EventBus {
    val listeners: List<Listener<*>>

    fun post(event: Event)
    fun <T> subscribe(listener: Listener<T>)
}

class EventBusImpl : EventBus {
    override val listeners: MutableList<Listener<*>> = mutableListOf()

    override fun post(event: Event) {
        listeners
            .filter { it.javaClass.genericSuperclass == event.javaClass }
            .forEach { it.onEventCasted(event) }
    }

    override fun <T> subscribe(listener: Listener<T>) {
        this.listeners.add(listener)
    }

}
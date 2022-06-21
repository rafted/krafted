package event

interface Event

data class Listener<T : Event> (
    val type: Class<T>,
    val action: (T) -> Unit
)

object EventBus {
    private val handlers = mutableListOf<Listener<*>>()

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
       this.handlers
           .filter { it.type == event::class.java }
           .forEach { listener -> listener.action(event) }
   }

}

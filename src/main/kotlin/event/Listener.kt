package event

interface Listener<T> {
    fun onEvent(event: T)

    fun onEventCasted(event: Event) {
        onEvent(event as T)
    }
}
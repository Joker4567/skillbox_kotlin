package ru.skillbox.a01_generics

class Queue<T> {
    private val queue: MutableList<T> = mutableListOf()

    fun enqueue(item: T) {
        queue.add(item)
    }

    fun dequeue(): T? {
        if (queue.isEmpty()) {
            return null
        }
        return queue.removeAt(0)
    }
}
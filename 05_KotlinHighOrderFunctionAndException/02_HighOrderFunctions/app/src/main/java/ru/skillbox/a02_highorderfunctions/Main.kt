package ru.skillbox.a02_highorderfunctions

fun main() {
    val first = Queue<Any?>()

    first.enqueue(10)
    first.enqueue("secondTeen")
    first.enqueue('3')
    first.enqueue(null)
    first.enqueue(50)

    //при помощи lambda выражений
    val second = first.filter { it is Int }
    val three = second::filter { it is Int }

    println(three.dequeue())
    println(three.dequeue())

    val ff = Queue<Int>()
    ff.enqueue(1)
    ff.enqueue(2)
    ff.enqueue(3)
    ff.enqueue(4)
    val foo = ff.filter(::predict)

    println(foo.dequeue())
    println(foo.dequeue())
}
//с использованием ссылки на функцию
fun predict(it: Int): Boolean {
    return it % 2 == 0
}
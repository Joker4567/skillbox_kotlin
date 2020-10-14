package ru.skillbox.a01_generics

fun main() {
    //region Вывод четных чисел
    val listInt = listOf<Int>(13, 26, 33, 48, 51, 68, 79)
    //println(numberList(listInt))
    val listDouble = listOf<Double>(1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7)
    //println(realNumberList(listDouble))
    val listFloat = listOf<Float>(1.11f, 2.22f, 3.33f, 4.44f, 5.55f, 6.66f, 7.77f)
    //println(realNumberList(listFloat))
    val listLong = listOf<Long>(
        122.2.toLong(),
        122.0.toLong()
    )
    println(realNumberList(listLong))
    //endregion

    //region Достаём данные из очереди
    val queue = Queue<String>()
    queue.enqueue("first")
    queue.enqueue("second")
    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())
    //endregion

    //region Tree
    val first: Result<Int, String>? = getResult(1)
    val second: Result<Any, String>? = getResult(0)
    println(first.toString())
    println(second.toString())
    //endregion
}
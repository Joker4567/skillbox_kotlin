package com.pstu.hello

var countPozitive:Int = 0

fun main() {
    var mutableList: MutableList<Int>
    mutableList = inputMutableList(inputCount()!!.toInt()).toMutableList()
    println("Итоговый список:")
    println(mutableList)
    countPositiveFun(mutableList)
    filterChet(mutableList)
    println("Кол-во уникальный чисел в коллекции: ${unicalNumber(mutableList)}")
    println("Сумма всех чисел в коллекции = ${mutableList.toList().sum()}")
    displayMap(runRecMap(mutableList, mutableList.toSet().sum()))
    displayNod(mutableList, mutableList.toSet().sum())
}
//Кол-во уникальных чисел
fun unicalNumber(list:MutableList<Int>) = list.toSet().size
//Выводим четные элементы коллекции
fun filterChet(list:MutableList<Int>){
    print("Список четных чисел: ")
    val filteredIdx = list.filter { it % 2 == 0 }
    if (filteredIdx.isNotEmpty()) println(filteredIdx) else println("null")
}
//Ввод числа N - кол-во элементов в коллекции
fun inputCount():Int?{
    var count: Int?
    print("Введите число: ")
    while(true) {
        count = readLine()?.toIntOrNull()
        if(count != null) break else println("Ошибка ввода числа, повторите ввод")
    }
    return count
}
//Заполнение коллекции
fun inputMutableList(n:Int):List<Int> {
    var list = mutableListOf<Int>()
    while(true){
        println("Введите очередное число:")
        readLine()?.toIntOrNull()
            ?.let { number ->
                    list.add(number)
            }
            ?: println("Вы ввели не число! Попробуйте снова")
        if(n == list.size) break //в случае заполнения массива выходим из цикла
    }
    return list
}
//Подсчёт положительных чисел
fun countPositiveFun(list:kotlin.collections.MutableList<Int>){
    list.forEach{ it -> if(it > 0) countPozitive++}
    println("Кол-во положительных чисел в массиве - $countPozitive")
}
//рекурсивная функция для вычисления НОД
tailrec fun recNod(a: Int, sum: Int): Int {
    return if (sum == 0) a else recNod(sum, a % sum)
}
//создание колекции Map с результатми расчёта НОД
fun runRecMap(list:MutableList<Int>, sum:Int):MutableMap<String,String>{
    println("Сумма уникальных чисел в коллекции: ${sum}")
    var mutableMap = mutableMapOf<String, String>()
    list.forEach{ number ->
        run {
            mutableMap.put("Число <$number> - ", "НОД <${recNod(number, sum)}>")
        }
    }
    return mutableMap
}
//Вывод коллекции Map
fun displayMap(map:MutableMap<String,String>){
    map.forEach { (key, value) ->
        println("$key $value")
    }
}
//Вывод в формате Число <>, Сумма <>, НОД <>
fun displayNod(list:MutableList<Int>, sum:Int){
    list.forEach{ number ->
        run {
            println("Число <$number>, Сумма <$sum>, НОД <${recNod(number, sum)}>")
        }
    }
}
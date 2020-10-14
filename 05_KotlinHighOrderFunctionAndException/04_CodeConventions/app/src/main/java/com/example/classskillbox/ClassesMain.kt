package com.example.classskillbox

import com.example.classskillbox.module4.Battle

fun main() {
    while (true) {
        print("Введите количество воинов: ")
        val teamCount = readLine()?.toIntOrNull() ?: continue
        val battle = Battle(teamCount)
        battle.startBattle()
    }
}

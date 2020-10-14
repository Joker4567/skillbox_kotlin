package com.example.classskillbox.module4

interface Warrior {
    val isKilled: Boolean
    val chanceOfDodging: Int
    val accuracy: Int
    fun attack(warrior: Warrior)
    fun takeDamage(damage: Int)
    fun getCurrentHealth(): Int
}
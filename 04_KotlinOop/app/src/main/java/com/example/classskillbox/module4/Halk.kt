package com.example.classskillbox.module4

class Halk(
    health: Int,
    chanceOfDodging: Int,
    accuracy: Int,
    weapon: AbstractWeapon
) : AbstractWarrior(health, chanceOfDodging, accuracy, weapon) {
    override fun toString(): String {
        return "Juggernaut[${super.toString()}]"
    }
}
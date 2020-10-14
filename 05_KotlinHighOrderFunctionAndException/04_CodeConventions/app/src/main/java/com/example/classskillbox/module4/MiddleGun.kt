package com.example.classskillbox.module4

class MiddleGun(
    health: Int,
    chanceOfDodging: Int,
    accuracy: Int,
    weapon: AbstractWeapon
) : AbstractWarrior(health, chanceOfDodging, accuracy, weapon) {
    override fun toString(): String {
        return "Gunner[${super.toString()}]"
    }
}

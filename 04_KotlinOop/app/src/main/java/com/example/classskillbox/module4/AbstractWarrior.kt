package com.example.classskillbox.module4

import android.util.Log

abstract class AbstractWarrior(
    var health: Int,//здоровье
    override val chanceOfDodging: Int,//шанс избежать урона
    override val accuracy: Int,//точность попадания
    private val weapon: AbstractWeapon//оружие
) : Warrior {
    override val isKilled: Boolean
        get() = health == 0

    init {
        //println("Рекрутирован $this")
        weapon.reload()
    }

    override fun attack(warrior: Warrior) {
        try {
            //..проверить, попадает ли он в соответствии с точностью воина и шансом избежать попадания врагом.
            if(chanceOfDodging < warrior.chanceOfDodging && accuracy < warrior.accuracy) {
                val damage = weapon.getShell().sumBy { it.getDamage() }
                //println("$this атакует $warrior")
                warrior.takeDamage(damage)
            }
        } catch (ex: NoAmmoException) {
            Log.w("AbstractWarrior.kt",ex.message)
            //println("Требуется перезарядка")
            weapon.reload()
        }
    }

    override fun takeDamage(damage: Int) {
        health = if (health - damage < 0) 0 else health - damage
        if (isKilled) {
            //println("$this понес урон $damage и погиб")
        } else {
            //println("$this понес урон $damage")
        }
    }

    override fun getCurrentHealth(): Int {
        return health
    }

    override fun toString(): String {
        return health.toString()
    }
}
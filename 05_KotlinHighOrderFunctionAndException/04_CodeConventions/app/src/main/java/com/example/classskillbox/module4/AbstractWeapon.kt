package com.example.classskillbox.module4

abstract class AbstractWeapon(
    val maxAmmo: Int,
    val fireType: FireType,
    val ammo: Ammo
) {
    private var ammoList: MutableList<Ammo> = mutableListOf()
    private val ammoIsEmpty: Boolean
        get() = ammoList.isEmpty()

    init {
//        println("Инициализировано оружие ${this.fireType}, магазин пуст")
    }

    abstract fun makeShell(): Ammo

    fun reload() {
        for (shell in 1..maxAmmo) {
            ammoList.add(makeShell())
        }
        // println("Заряжен ${ammoList.size} заряд ${fireType.ammo}")
    }

    fun getShell(): List<Ammo> {
        if (ammoIsEmpty) throw NoAmmoException("Боекомплект пуст")
        var shells: MutableList<Ammo> = mutableListOf()
        var count = fireType.repeat
        while (count > 0) {
            shells.add(ammoList.last())
            ammoList.removeAt(ammoList.lastIndex)
            count--
        }
        return shells
    }
}

package com.example.classskillbox.module4

object Weapons {
    val machineGun = object :
        AbstractWeapon(
            maxAmmo = 100,
            fireType = FireType.BurstsShooting(repeat = 10),
            ammo = Ammo.BULLET
        ) {
        override fun makeShell(): Ammo {
            return ammo
        }
    }
    val grenadeLauncher = object :
        AbstractWeapon(
            maxAmmo = 1,
            fireType = FireType.SingleShooting(),
            ammo = Ammo.GRENADE
        ) {
        override fun makeShell(): Ammo {
            return ammo
        }
    }
    val crossbow = object :
        AbstractWeapon(
            maxAmmo = 1,
            fireType = FireType.SingleShooting(),
            ammo = Ammo.ARROW
        ) {
        override fun makeShell(): Ammo {
            return ammo
        }
    }
    val arm = object :
        AbstractWeapon(
            maxAmmo = 1,
            fireType = FireType.SingleShooting(),
            ammo = Ammo.DART
        ) {
        override fun makeShell(): Ammo {
            return ammo
        }
    }
    val catapult = object :
        AbstractWeapon(
            maxAmmo = 9,
            fireType = FireType.BurstsShooting(repeat = 9),
            ammo = Ammo.CAT
        ) {
        override fun makeShell(): Ammo {
            return ammo
        }
    }
}

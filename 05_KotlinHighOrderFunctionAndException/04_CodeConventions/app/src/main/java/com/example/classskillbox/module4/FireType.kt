package com.example.classskillbox.module4

sealed class FireType(
    open val repeat: Int
) {
    data class SingleShooting(
        override val repeat: Int = 1
    ) :
        FireType(repeat)

    data class BurstsShooting(
        override val repeat: Int
    ) :
        FireType(repeat)
}

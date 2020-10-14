package com.example.classskillbox.module4

import kotlin.random.Random

class Battle(private val countOfWarrior: Int) {
    private var arrayTeams = Array(2) { Team(countOfWarrior) }
    private var battleFinished: Boolean = false
    private var turnFirst: Int = Random.nextInt(2)
    private val firstTeamHealth: Int
        get() = arrayTeams[0].getWarriorList().sumBy { it.getCurrentHealth() }
    private val secondTeamHealth: Int
        get() = arrayTeams[1].getWarriorList().sumBy { it.getCurrentHealth() }

    fun startBattle() {
        if (turnFirst == 1) {
            arrayTeams.reverse()
        }
        println("Начало битвы")
        while (!battleFinished) {
            shuffled()
            val battleState = getState()
            battleState.printInConsole()
            if (battleState !is BattleState.CurrentState) {
                battleFinished = true
            }
        }
    }

    private fun getState(): BattleState {
        return if (firstTeamHealth == 0) {
            BattleState.SecondTeamWin("Победила вторая команда")
        } else if (secondTeamHealth == 0) {
            BattleState.FirstTeamWin("Победила первая команда")
        } else if (firstTeamHealth != 0 && secondTeamHealth != 0) {
            BattleState.CurrentState("Progress(commandAHealth=$firstTeamHealth, commandBHealth=$secondTeamHealth)")
        } else {
            BattleState.Debuxar("Ничья")
        }
    }

    private fun shuffled() {
        println("Совершаем ход")
        arrayTeams.forEach { it.shuffleWarriorList() }
        arrayTeams.forEach { team ->
            team.getWarriorList().forEach { warrior ->
                if (!warrior.isKilled) {
                    val targetWarrior: Warrior? = if (team == arrayTeams[0]) {
                        arrayTeams[1].getWarriorList().lastOrNull { !it.isKilled }
                    } else {
                        arrayTeams[0].getWarriorList().lastOrNull { !it.isKilled }
                    }
                    if (targetWarrior != null) {
                        warrior.attack(targetWarrior)
                    }
                }
            }
        }
    }
}
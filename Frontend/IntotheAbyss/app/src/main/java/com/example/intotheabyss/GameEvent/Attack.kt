package com.example.intotheabyss.GameEvent

import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.utils.EventType

/**
 * @param player1 The player doing the attacking
 * @param player2 The player being attacked
 */
class Attack(internal val player1: Player, internal val player2: Player) : GameEvent()    {
    override val type = EventType.ATTACK
}
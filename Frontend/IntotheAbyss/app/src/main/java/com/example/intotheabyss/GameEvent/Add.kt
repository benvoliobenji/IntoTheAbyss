package com.example.intotheabyss.GameEvent

import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.utils.EventType

/**
 * @param player1 The player originating the add. AKA they are adding player2 to their group.
 * @param player2 The player getting added to the group
 */
class Add(private val player1: Player, private val player2: Player) : GameEvent()    {
    override val type = EventType.REMOVE
}
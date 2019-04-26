package com.example.intotheabyss.GameEvent

import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.utils.EventType

/**
 * @param player The player being removed from their group.
 */
class Kick(internal val player: Player) : GameEvent()    {
    override val type = EventType.KICK
}
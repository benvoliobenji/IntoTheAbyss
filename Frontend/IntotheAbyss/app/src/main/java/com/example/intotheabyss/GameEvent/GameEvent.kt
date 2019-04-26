package com.example.intotheabyss.GameEvent

import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.utils.EventType

abstract class GameEvent()    {
    abstract val type: EventType
}
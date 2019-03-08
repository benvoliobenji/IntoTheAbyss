package com.example.intotheabyss.game

import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.player.Player

class GameState {
    var myPlayer: Player? = null
    var level = arrayOf<Array<Tile>>()
    var loading: Boolean = true
}
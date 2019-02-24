package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

class Wall : Tile() {
    val type = 2
    override val typel = TileTypes.WALL

    init {
        canHold = false
        isPassable = false
    }
}
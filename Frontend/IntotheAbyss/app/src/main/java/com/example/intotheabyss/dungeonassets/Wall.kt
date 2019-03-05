package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

class Wall : Tile() {
    val type = 2
    override val type1 = TileTypes.WALL

    init {
        canHold = false
        isPassable = false
    }
}
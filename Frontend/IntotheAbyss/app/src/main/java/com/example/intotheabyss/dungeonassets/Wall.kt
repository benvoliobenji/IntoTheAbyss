package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

class Wall : Tile() {
    override val type = TileTypes.WALL

    init {
        canHold = false
        isPassable = false
    }
}
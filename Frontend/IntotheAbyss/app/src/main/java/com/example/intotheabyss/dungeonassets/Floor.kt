package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

class Floor : Tile() {
    val type = 1
    override val type1 = TileTypes.FLOOR

    init {
        canHold = true
        isPassable = true
    }
}

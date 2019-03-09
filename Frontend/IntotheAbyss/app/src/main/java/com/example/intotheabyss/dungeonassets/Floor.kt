package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

class Floor : Tile() {
    override val type = TileTypes.FLOOR

    init {
        canHold = true
        isPassable = true
    }
}

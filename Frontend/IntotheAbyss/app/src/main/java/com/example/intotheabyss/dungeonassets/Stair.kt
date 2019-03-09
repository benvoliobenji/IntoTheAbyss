package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

class Stair: Tile() {
    override val type: TileTypes = TileTypes.STAIR

    init {
        canHold = false
        isPassable = true
    }
}
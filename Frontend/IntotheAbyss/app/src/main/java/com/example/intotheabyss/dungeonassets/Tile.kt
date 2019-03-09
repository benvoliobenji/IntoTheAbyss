package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

abstract class Tile(internal var canHold: Boolean=false, internal var isPassable: Boolean=false) {
    abstract val type: TileTypes
}


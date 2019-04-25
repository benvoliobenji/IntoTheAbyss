package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

/**
 * The Tile abstract class is what is used by GameState and the majority of the display logic to represent different
 * tiles in the 2D dungeon array.
 * @author Benjamin Vogel
 */
abstract class Tile(internal var canHold: Boolean=false, internal var isPassable: Boolean=false) {
    abstract val type: TileTypes
}


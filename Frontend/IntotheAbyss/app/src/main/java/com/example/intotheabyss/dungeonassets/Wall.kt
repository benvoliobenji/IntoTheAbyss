package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

/**
 * A specific class Wall that inherits from the Tile superclass and will be used to represent Walls for the user and
 * game logic.
 *
 * @constructor Constructs a default Wall object which is of TileType WALL, cannot hold object, and is not passable
 * @author Benjamin Vogel
 */
class Wall : Tile() {
    override val type = TileTypes.WALL

    init {
        canHold = false
        isPassable = false
    }
}
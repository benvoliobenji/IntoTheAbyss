package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

/**
 *  This is the specific class Floor which will be used to display the dungeon to the user,
 *
 *  @constructor Creates a Floor object that is of TyleType FLOOR, can hold, and is passable
 *  @author Benjamin Vogel
 */
class Floor : Tile() {
    override val type = TileTypes.FLOOR

    init {
        canHold = true
        isPassable = true
    }
}

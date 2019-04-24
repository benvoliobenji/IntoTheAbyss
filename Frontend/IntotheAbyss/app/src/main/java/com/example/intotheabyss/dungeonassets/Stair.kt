package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.utils.TileTypes

/**
 * This is a specific class Stair that inherits from the Tile superclass. This holds unique properties from Tile
 * and will be used to display stairs to the player,
 *
 * @constructor Creates a default stair type which is of TileType STAIR, cannot hold objects, and is passable
 * @author Benjamin Vogel
 */
class Stair: Tile() {
    override val type: TileTypes = TileTypes.STAIR

    init {
        canHold = false
        isPassable = true
    }
}
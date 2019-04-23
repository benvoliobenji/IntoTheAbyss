package com.example.intotheabyss.game.levelhandler

import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall

class LevelHandler: LevelHandlerInterface {


    /**
     * Method to generate a sample level. Just makes a big wide room that can be traversed by the player.
     * Places no stairs.
     *
     * @param xSize The width of the room
     * @param ySize The height of the room
     * @return A 2D array of Tiles. WALLs for walls and FLOORs for floors
     */
    override fun genericLevel(xSize: Int, ySize: Int): Array<Array<Tile>> {
        val lvlArray = Array(ySize) { Array(xSize) { tile } }

        val wall = Wall()
        val floor = Floor()

        for (i in 0 until lvlArray.size) {
            for (j in 0 until lvlArray[i].size) {
                if ((i == 0) or (i == lvlArray.size-1) or (j == 0) or (j == lvlArray[0].size-1)) {
                    lvlArray[i][j] = wall
                } else {
                    lvlArray[i][j] = floor
                }
            }
        }

        return lvlArray
    }

    companion object {
        private var tile: Tile = Wall()
    }

}
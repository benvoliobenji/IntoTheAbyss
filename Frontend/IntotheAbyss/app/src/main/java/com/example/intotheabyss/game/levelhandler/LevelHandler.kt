package com.example.intotheabyss.game.levelhandler

import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.GameView

class LevelHandler: LevelHandlerInterface {


    override fun genericLevel(xSize: Int, ySize: Int): Array<Array<Tile>> {
        var lvlArray = Array(ySize) { Array(xSize) { tile } }

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
package com.example.intotheabyss.game.levelhandler

import com.example.intotheabyss.dungeonassets.Tile

interface LevelHandlerInterface {
    fun genericLevel(xSize: Int, ySize: Int): Array<Array<Tile>>
}
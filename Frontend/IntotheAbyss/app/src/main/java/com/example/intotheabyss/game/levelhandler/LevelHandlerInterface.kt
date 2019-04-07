package com.example.intotheabyss.game.levelhandler

import android.graphics.Point
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.player.Player

interface LevelHandlerInterface {
    fun genericLevel(xSize: Int, ySize: Int): Array<Array<Tile>>
//    fun checkNewLevel(g: GameState, gc: GameController)
//    fun updateBoundaries(player: Player): Point
}
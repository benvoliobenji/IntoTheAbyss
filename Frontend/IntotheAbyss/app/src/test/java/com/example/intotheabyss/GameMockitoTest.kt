package com.example.intotheabyss

import android.content.Context
import android.view.MotionEvent
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.levelhandler.LevelHandler
import com.example.intotheabyss.game.levelhandler.LevelHandlerInterface
import com.example.intotheabyss.game.player.Player
import org.junit.Before
//import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.mockito.*

class GameMockitoTest {

    @Mock
    lateinit var gameView: GameView

    @Mock
    lateinit var player: Player

    @Mock
    lateinit var gameController: GameController

    @Mock
    lateinit var system: System

    @Mock
    lateinit var lvlHandler: LevelHandlerInterface

    var lvlArray = Array(3) { Array(3) { tile } }

    @Before
    fun initObjects() {
        gameView.sHeight = 1080
        gameView.sWidth = 1920

        gameController.lastTimeAction = 0
        gameController.thisTimeAction = 250
        gameController.input = MotionEvent.obtain(5, 5, MotionEvent.ACTION_DOWN, 1800f, 50f, 0)


        for (i in 0 until lvlArray.size) {
            for (j in 0 until lvlArray[i].size) {
                if ((i == 0) or (i == lvlArray.size-1) or (j == 0) or (j == lvlArray[0].size-1)) {
                    lvlArray[i][j] = Wall()
                } else {
                    lvlArray[i][j] = Floor()
                }
            }
        }
    }

    @Test
    fun testCheckNewLevel() {

    }

    @Test
    fun testGenericLevel() {


        Mockito.`when`(lvlHandler.genericLevel(3,3)).thenReturn(lvlArray)
    }

    companion object {
        private var tile: Tile = Wall()
    }
}
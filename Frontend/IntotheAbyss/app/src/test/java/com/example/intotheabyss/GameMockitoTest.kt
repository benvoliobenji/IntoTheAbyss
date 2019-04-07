package com.example.intotheabyss

import android.content.Context
import android.view.MotionEvent
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.levelhandler.LevelHandler
import com.example.intotheabyss.game.levelhandler.LevelHandlerInterface
import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.updateverification.UpdateVerification
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
//import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.mockito.*

class GameMockitoTest {

    companion object {
        var gameState = GameState()
        var mGameController = mock<GameController>()
        var mGameView = mock<GameView>()
        var updateVerification = UpdateVerification(gameState.myPlayer.x, gameState.myPlayer.y,
            gameState.myPlayer.floorNumber)
    }

//    @Mock
//    lateinit var gameView: GameView
//
//    @Mock
//    lateinit var player: Player
//
//    @Mock
//    lateinit var gameController: GameController
//
//    @Mock
//    lateinit var system: System
//
//    @Mock
//    lateinit var lvlHandler: LevelHandlerInterface

//    var lvlArray = Array(3) { Array(3) { tile } }
//
//    @Before
//    fun initObjects() {
//        gameView.sHeight = 1080
//        gameView.sWidth = 1920
//
//        gameController.lastTimeAction = 0
//        gameController.thisTimeAction = 250
//        gameController.input = MotionEvent.obtain(5, 5, MotionEvent.ACTION_DOWN, 1800f, 50f, 0)
//
//
//        for (i in 0 until lvlArray.size) {
//            for (j in 0 until lvlArray[i].size) {
//                if ((i == 0) or (i == lvlArray.size-1) or (j == 0) or (j == lvlArray[0].size-1)) {
//                    lvlArray[i][j] = Wall()
//                } else {
//                    lvlArray[i][j] = Floor()
//                }
//            }
//        }
//    }

    @Before
    fun setupTests() {
        mGameView.setGameState(gameState)
    }

    @Test
    fun testCheckNewLevel() {
        whenever(mGameController.getAction(1800f,50f,MotionEvent.ACTION_DOWN)).thenReturn(1)

        mGameView.checkNewLevel()

        Assert.assertEquals(gameState.loading, true)
    }

    @Test
    fun testGenericLevel() {


//        Mockito.`when`(lvlHandler.genericLevel(3,3)).thenReturn(lvlArray)
    }
}
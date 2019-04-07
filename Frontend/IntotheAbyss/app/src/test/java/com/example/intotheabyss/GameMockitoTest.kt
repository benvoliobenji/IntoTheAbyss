package com.example.intotheabyss

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.view.MotionEvent
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.drawplayer.DrawPlayer
import com.example.intotheabyss.game.drawplayer.DrawPlayerInterface
import com.example.intotheabyss.game.drawplayer.gameView
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.gamecontroller.GameControllerInterface
import com.example.intotheabyss.game.levelhandler.LevelHandler
import com.example.intotheabyss.game.levelhandler.LevelHandlerInterface
import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.game.player.PlayerInterface
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.updateverification.UpdateVerification
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
//import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.mockito.*
import java.util.regex.Pattern.matches

class GameMockitoTest {

    companion object {
        var gameState = GameState()
        var mGameController = mock<GameController>()
        var mGameView = mock<GameView>()
        var mDrawPlayer = mock<DrawPlayer>()
        var mPlayer = mock<PlayerInterface>()
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
        mGameView.player = Player()
//        gameState.myPlayer = gameView!!.player!!
    }

    /**
     * Test to ensure that on player Action we indicate we passed the level
     */
    @Test
    fun testCheckNewLevel() {
        whenever(mGameController.getAction(1800f,50f,MotionEvent.ACTION_DOWN)).thenReturn(1)


        GameMockitoTest.gameState.loading = false
//        gameState.loading = true

//        GameMockitoTest.mGameController.getAction(1800f, 50f, MotionEvent.ACTION_DOWN)
        GameMockitoTest.mGameView.checkNewLevel(GameMockitoTest.gameState, GameMockitoTest.mGameController)

        Assert.assertEquals(GameMockitoTest.gameState.loading, true)
    }

    @Test
    fun updateVerificationInvokesNewDungeonLevelOnFloorTransition() {
        UpdateHandlerTests.gameState.myPlayer.floorNumber = 1
        UpdateHandlerTests.updateVerification.verifyGameState(
            UpdateHandlerTests.gameState,
            UpdateHandlerTests.mockKryoNetwork,
            UpdateHandlerTests.mockVolleyNetwork
        )

        verify(UpdateHandlerTests.mockVolleyNetwork, times(1)).retrieveNewDungeonLevel(
            UpdateHandlerTests.gameState.myPlayer.floorNumber,
            UpdateHandlerTests.mockKryoNetwork
        )

    }

    @Test
    fun updateOffset() {
        whenever(mDrawPlayer.updateBoundaries(Player())).thenReturn(Point(5,6))

        mGameView.testUpdate(mDrawPlayer, mGameController)

//        Assert.assertEquals(mGameView.minX, 5)
//        Assert.assertEquals(mGameView.minY, 6)
        Assert.assertEquals(mGameView.offsetPoint.x, 5)

//        Mockito.`when`(lvlHandler.genericLevel(3,3)).thenReturn(lvlArray)
    }
}
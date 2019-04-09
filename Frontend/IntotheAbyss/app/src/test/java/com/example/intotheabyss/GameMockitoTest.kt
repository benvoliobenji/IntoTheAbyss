package com.example.intotheabyss

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.util.AttributeSet
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
import com.example.intotheabyss.game.gamecontroller.GameControllerHelperInterface
import com.example.intotheabyss.game.gamecontroller.GameControllerInterface
import com.example.intotheabyss.game.levelhandler.LevelHandler
import com.example.intotheabyss.game.levelhandler.LevelHandlerInterface
import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.game.player.PlayerInterface
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.updateverification.UpdateVerification
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
//import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.mockito.*
import java.util.jar.Attributes
import java.util.regex.Pattern.matches

class GameMockitoTest {
    var gameView = GameView
//    var input = MotionEvent.obtain(10, 10, MotionEvent.ACTION_DOWN, 1800f, 100f, 0)

    companion object {
        var gameController: GameController? = null
        var me = mock<MotionEvent>()

        var gameState = GameState()
        var mGameView = mock<GameView>()
        var mGameControllerHelper = mock<GameControllerHelperInterface>()
    }


    @Before
    fun setupTests() {

        gameState = GameState()
        gameState.loading = false
        gameController = GameController(mGameView)
    }

    @Test
    fun testGetActionSucceed() {
//        var input = MotionEvent.obtain(10, 10, MotionEvent.ACTION_DOWN, 1800f, 100f, 0)

        whenever(mGameControllerHelper.checkActionRange(1800f, 100f, 0)).thenReturn(true)

        gameController!!.gcHelper = mGameControllerHelper

        Assert.assertEquals(gameController!!.getAction(1800f, 100f, 0), 1)
    }

    @Test
    fun testGetActionFail() {
        whenever(mGameControllerHelper.checkActionRange(900f, 100f, 0)).thenReturn(false)

        gameController!!.gcHelper = mGameControllerHelper

        Assert.assertEquals(gameController!!.getAction(900f, 100f, 0), 0)
    }

}
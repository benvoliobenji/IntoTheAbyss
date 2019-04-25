package com.example.intotheabyss

import android.view.MotionEvent
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.gamecontroller.GameControllerHelperInterface
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
//import com.nhaarman.mockitokotlin2.*
import org.junit.Test

//class GameMockitoTest {
//    var gameView = GameView
////    var input = MotionEvent.obtain(10, 10, MotionEvent.ACTION_DOWN, 1800f, 100f, 0)
//
//    companion object {
//        var gameController: GameController? = null
//        var me = mock<MotionEvent>()
//
//        var gameState = GameState()
//        var mGameView = mock<GameView>()
//        var mGameControllerHelper = mock<GameControllerHelperInterface>()
//    }
//
//
//    @Before
//    fun setupTests() {
//
//        gameState = GameState()
//        gameState.loading = false
//        gameController = GameController(mGameView)
//    }
//
//    @Test
//    fun testGetActionSucceed() {
////        var input = MotionEvent.obtain(10, 10, MotionEvent.ACTION_DOWN, 1800f, 100f, 0)
//
//        whenever(mGameControllerHelper.checkActionRange(1800f, 100f, 0)).thenReturn(true)
//
//        gameController!!.gcHelper = mGameControllerHelper
//
//        Assert.assertEquals(gameController!!.getAction(1800f, 100f, 0), 1)
//    }
//
//    @Test
//    fun testGetActionFail() {
//        whenever(mGameControllerHelper.checkActionRange(900f, 100f, 0)).thenReturn(false)
//
//        gameController!!.gcHelper = mGameControllerHelper
//
//        Assert.assertEquals(gameController!!.getAction(900f, 100f, 0), 0)
//    }
//
//}
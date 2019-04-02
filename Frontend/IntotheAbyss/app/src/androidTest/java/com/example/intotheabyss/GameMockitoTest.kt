package com.example.intotheabyss

import android.view.MotionEvent
import com.example.intotheabyss.game.gamecontroller.GameController
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class GameMockitoTest {

    @Test
    fun controllerTest() {
        val event: MotionEvent = MotionEvent.obtain(5, 5, MotionEvent.ACTION_DOWN, 1800f, 50f, 1)

        val controller = mock<GameController>()

        assert(controller.getAction(1800f, 0f, MotionEvent.ACTION_DOWN) == 1)
    }

}
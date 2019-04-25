package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.view.MotionEvent

interface GameControllerInterface {
    fun setEvent(event: MotionEvent): MotionEvent
    fun getAction(x: Float, y: Float, action: Int): Int
    fun updatePlayerLocation()
    fun drawController(canvas: Canvas)
    fun getPList(x: Float, y: Float, action: Int, bool: Boolean): Boolean
    fun drawExitButton(canvas: Canvas)
}
package com.example.intotheabyss.game.gamecontroller

import android.view.MotionEvent

interface GameControllerInterface {
    fun setEvent(event: MotionEvent): MotionEvent
    fun getAction(x: Float, y: Float, action: Int): Int
    fun updatePlayerLocation()
}
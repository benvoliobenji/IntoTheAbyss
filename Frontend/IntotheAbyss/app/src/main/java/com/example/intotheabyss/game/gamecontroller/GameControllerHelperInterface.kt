package com.example.intotheabyss.game.gamecontroller

import android.graphics.Point
import android.view.MotionEvent


interface GameControllerHelperInterface    {
    fun checkActionRange(x: Float, y: Float, action: Int, input: MotionEvent): Boolean
    fun checkMovementDir(input: MotionEvent): Point
}
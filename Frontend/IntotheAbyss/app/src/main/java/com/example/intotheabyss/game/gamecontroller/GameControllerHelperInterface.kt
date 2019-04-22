package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.graphics.Point
import android.view.MotionEvent


interface GameControllerHelperInterface    {
    fun checkActionRange(x: Float, y: Float, action: Int): Boolean
    fun checkMovementDir(curX: Float, curY: Float): Point
    fun drawMovement(canvas: Canvas)
    fun drawAction(canvas: Canvas)
    fun drawPlayerBoardButton(canvas: Canvas)
    fun checkPlayerListButton(x: Float, y: Float, action: Int, boolean: Boolean): Boolean
}
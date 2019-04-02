package com.example.intotheabyss.game.player

import android.graphics.Bitmap
import android.graphics.Canvas

interface PlayerInterface {
    fun setImage(im: Bitmap)
    fun draw(canvas: Canvas, x: Int, y: Int)
}

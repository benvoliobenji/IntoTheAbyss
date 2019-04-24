package com.example.intotheabyss.game.player

import android.graphics.Bitmap
import android.graphics.Canvas

/**
 * The PlayerInterface. This is the interface that declares the functions the any class inheriting from PlayerInterface
 * must implement. Designed to allow for dependency injection.
 *
 * @author Benjamin Vogel
 */
interface PlayerInterface {
    fun setImage(im: Bitmap)
    fun draw(canvas: Canvas, x: Int, y: Int)
}

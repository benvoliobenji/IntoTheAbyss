package com.example.intotheabyss.player

import android.graphics.Bitmap
import android.graphics.Canvas
import java.util.*

import kotlin.random.Random

class Player(var image: Bitmap) {
    var playerID: String = Random.nextInt(0, 1000000).toString()
    var floorNumber: Int = 0
    private var x: Int = 0
    private var y: Int = 0

    //Getters and setters are automatically generated if non-private variables.
    //This is just testing to confirm.
    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }

    fun setX(nX: Int) {
        x = nX
    }

    fun setY(nY: Int) {
        y = nY
    }

    /*
    Method to draw player to the canvas
     */
    fun draw(canvas: Canvas, x: Int, y: Int) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }
}
package com.example.intotheabyss.player

import android.graphics.Bitmap
import android.graphics.Canvas

import java.lang.Exception
import java.util.*

import kotlin.random.Random

class Player {
    var playerID: String = Random.nextInt(0, 1000000).toString()
    var floorNumber: Int = 1
    private var x: Int = 0
    private var y: Int = 0
    private var image: Bitmap? = null

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

    fun setImage(im: Bitmap) {
        image = im
    }

    /*
    Method to draw player to the canvas
     */
    fun draw(canvas: Canvas, x: Int, y: Int) {
        try {
            canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
        } catch (e: Exception) {
            print(e.printStackTrace())
        }
    }
}

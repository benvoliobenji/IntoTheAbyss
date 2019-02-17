package com.example.intotheabyss.player

import android.graphics.Bitmap
import android.graphics.Canvas

class Player(var image: Bitmap) {
    //So in kotlin, we don't need getters and setters?
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
    fun draw(canvas: Canvas, x: Int, y: Int)    {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }
}
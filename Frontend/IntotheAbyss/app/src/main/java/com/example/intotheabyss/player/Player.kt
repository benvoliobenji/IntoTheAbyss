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

class Player(var image: Bitmap) {
    //So in kotlin, we don't need getters and setters?
    var floorNumber: Int = 0
    private var x: Int = 0
    private var y: Int = 0
import java.lang.Exception
import java.util.*

import kotlin.random.Random

class Player() {
    var playerID: String = Random.nextInt(0, 1000000).toString()
    var floorNumber: Int = 0
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


    /*
    Method to draw player to the canvas
     */
    fun draw(canvas: Canvas, x: Int, y: Int)    {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)

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

>>>>>>> 174633a56e0e8f9946f053207d89514bb75ad192
=======
>>>>>>> bea872b5d6e0668930268bc416a9f0040c6047a5
>>>>>>> 0fdfd46523401060f73c3e4664c45a857f184cc9
    }
}

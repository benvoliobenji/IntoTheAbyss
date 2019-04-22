package com.example.intotheabyss.game.player

import android.graphics.Bitmap
import android.graphics.Canvas

import java.lang.Exception

import kotlin.random.Random

class Player: PlayerInterface {
    var playerName: String = ""
    var playerID: String = Random.nextInt(0, 1000000).toString()
    var health = 10
    var floorNumber: Int = 0
    var x: Int = 1
    var y: Int = 1
    private var image: Bitmap? = null
    var actionStatus = 0

    constructor() {
        this.playerName = ""
        this.playerID = ""
        this.health = 10
        this.floorNumber = 0
        this.x = 1
        this.y = 1
        this.image = null
    }

    constructor(playerName: String, playerID: String, floorNumber: Int, xPos: Int, yPos: Int) {
        this.playerName = playerName
        this.playerID = playerID
        this.health = 10
        this.floorNumber = floorNumber
        this.x = xPos
        this.y = yPos
    }

    constructor(playerName: String, playerID: String, health: Int, floorNumber: Int, xPos: Int, yPos: Int) {
        this.playerName = playerName
        this.playerID = playerID
        this.health = health
        this.floorNumber = floorNumber
        this.x = xPos
        this.y = yPos
    }

    override fun setImage(im: Bitmap) {
        image = im
    }

    /*
    Method to draw player to the canvas
     */
    override fun draw(canvas: Canvas, x: Int, y: Int) {
        try {
            canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
        } catch (e: Exception) {
            print(e.printStackTrace())
        }
    }
}

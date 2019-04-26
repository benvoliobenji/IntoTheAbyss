package com.example.intotheabyss.game.player

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.intotheabyss.game.entity.player.PlayerInterface

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
    var group: HashMap<String, Player> = hashMapOf()
    var isModerator = false
    var isGroupLeader = false

    constructor() {
        this.playerName = ""
        this.playerID = ""
        this.health = 10
        this.floorNumber = 0
        this.x = 1
        this.y = 1
        this.image = null

        group.clear()
    }

    constructor(playerName: String, playerID: String, floorNumber: Int, xPos: Int, yPos: Int) {
        this.playerName = playerName
        this.playerID = playerID
        this.health = 10
        this.floorNumber = floorNumber
        this.x = xPos
        this.y = yPos

        group.clear()
    }

    constructor(playerName: String, playerID: String, health: Int, floorNumber: Int, xPos: Int, yPos: Int) {
        this.playerName = playerName
        this.playerID = playerID
        this.health = health
        this.floorNumber = floorNumber
        this.x = xPos
        this.y = yPos

        group.clear()
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

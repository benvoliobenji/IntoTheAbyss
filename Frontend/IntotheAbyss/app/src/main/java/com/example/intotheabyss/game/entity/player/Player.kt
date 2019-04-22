package com.example.intotheabyss.game.entity.player

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.intotheabyss.game.entity.Entity

import java.lang.Exception

import kotlin.random.Random

class Player: PlayerInterface, Entity {
    var playerName: String = ""
    var party: HashMap<String, Player> = hashMapOf()
    private var image: Bitmap? = null

    constructor(): super("", 1, 1, 0, 10) {
        playerName = ""
        this.image = null
    }

    constructor(playerName: String, playerID: String, floorNumber: Int, xPos: Int, yPos: Int):
            super(playerID, xPos, yPos, floorNumber, 10) {
        this.playerName = playerName
    }

    constructor(playerName: String, playerID: String, health: Int, floorNumber: Int, xPos: Int, yPos: Int):
            super(playerID, xPos, yPos, floorNumber, health) {
        this.playerName = playerName
    }

    constructor(player: Player) : super(player.ID, player.x, player.y, player.floor, player.health) {
        this.playerName = player.playerName
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

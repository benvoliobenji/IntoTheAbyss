package com.example.intotheabyss.game.player

import android.graphics.Bitmap
import android.graphics.Canvas

import java.lang.Exception

import kotlin.random.Random

/**
 * The Player class implements the PlayerInterface and creates and object that will represent the User and the other
 * heros the User will see when playing the app. This holds values necessary for display and game logic such as
 * name, ID, coordinates, health, floor, and image to draw the player to the canvas.
 *
 * @constructor Generates a default player with no name, ID, 10 health, no image, and a start coordinate of (1, 1)
 * @author Benjamin Vogel
 */
class Player: PlayerInterface {
    var playerName: String = ""
    var playerID: String = ""
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

    /**
     * Another constructor that will set more values given they are provided.
     * @param playerName A name to be displayed above the player.
     * @param playerID The unique ID that will be used for referencing the player client and server-side.
     * @param floorNumber The floor the player is currently on.
     * @param xPos The current x-position the player is at.
     * @param yPos The current y-position the player is at.
     */
    constructor(playerName: String, playerID: String, floorNumber: Int, xPos: Int, yPos: Int) {
        this.playerName = playerName
        this.playerID = playerID
        this.health = 10
        this.floorNumber = floorNumber
        this.x = xPos
        this.y = yPos
    }

    /**
     * Another constructor that allows for the user to provide health as well as the other parameters.
     * @param playerName A name to be displayed above the player.
     * @param playerID The unique ID that will be used for referencing the player client- and server-side
     * @param health The current amount of health the player has.
     * @param floorNumber The floor the player is currently on.
     * @param xPos The current x-position the player is at.
     * @param yPos The current y-position the player is at.
     */
    constructor(playerName: String, playerID: String, health: Int, floorNumber: Int, xPos: Int, yPos: Int) {
        this.playerName = playerName
        this.playerID = playerID
        this.health = health
        this.floorNumber = floorNumber
        this.x = xPos
        this.y = yPos
    }

    /**
     * Sets the current image that will allow the Player to be displayed on the Canvas.
     * @param im A bitmap of the Player to be used to draw.
     */
    override fun setImage(im: Bitmap) {
        image = im
    }

    /**
     * Draws the current player on the canvas at the given x- and y-coordinates.
     * @param canvas The Canvas to which the player's image should be drawn.
     * @param x The x-coordinate to draw the player.
     * @param y The y-coordinate to draw the player.
     */
    override fun draw(canvas: Canvas, x: Int, y: Int) {
        try {
            canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
        } catch (e: Exception) {
            print(e.printStackTrace())
        }
    }
}

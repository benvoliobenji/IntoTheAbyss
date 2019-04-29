package com.example.intotheabyss.game.entity.player

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.EntityType
import java.lang.Exception

/**
 * The Player class implements the PlayerInterface and creates and object that will represent the User and the other
 * heroes the User will see when playing the app. This holds values necessary for display and game logic such as
 * name, ID, coordinates, health, floor, and image to draw the player to the canvas.
 *
 * @constructor Generates a default player with no name, ID, 10 health, no image, and a start coordinate of (1, 1)
 * @author Benjamin Vogel
 */
class Player: PlayerInterface, Entity {
    var playerName: String = ""
    var party: MutableList<Player> = mutableListOf()
    var role: Role = Role.PLAYER
    private var image: Bitmap? = null

    constructor(): super("", 1, 1, 0, 0, 0, 10, 0, EntityType.PLAYER) {
        playerName = ""
        this.party.clear()
        this.role = Role.PLAYER
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
    constructor(playerName: String, playerID: String, floorNumber: Int, xPos: Int, yPos: Int):
            super(playerID, xPos, yPos, 0, 0, floorNumber, 10, 0, EntityType.PLAYER) {
        this.playerName = playerName
        this.party.clear()
        this.role = Role.PLAYER
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
    constructor(playerName: String, playerID: String, health: Int, floorNumber: Int, xPos: Int, yPos: Int):
            super(playerID, xPos, yPos, 0, 0, floorNumber, health, 0, EntityType.PLAYER) {
        this.playerName = playerName
        this.party.clear()
        this.role = Role.PLAYER
    }

    /**
     * Another constructor that allows for the user to provide all the parameters except bitmap
     * @param playerName A name to be displayed above the player.
     * @param playerID The unique ID that will be used for referencing the player client- and server-side
     * @param health The current amount of health the player has.
     * @param floorNumber The floor the player is currently on.
     * @param xPos The current x-position the player is at.
     * @param yPos The current y-position the player is at.
     * @param party The list of Player's currently in the Player's party.
     * @param role The role of the Player.
     */
    constructor(playerName: String, playerID: String, health: Int, floorNumber: Int, xPos: Int,
                yPos: Int, party: MutableList<Player>, role: Role): super(playerID, xPos, yPos, 0, 0,
                floorNumber, health, 0, EntityType.PLAYER) {
        this.playerName = playerName
        this.party = party
        this.role = role
    }

    /**
     * Another constructor that allows for the user to create a Player object from another Player object.
     * @param player Another Player object.
     */
    constructor(player: Player) : super(player.ID, player.x, player.y, 0, 0, player.floor,
                player.health, 0, EntityType.PLAYER) {
        this.playerName = player.playerName
        this.party = player.party
        this.role = player.role
        this.image = player.image
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
            canvas.drawBitmap(image!!, x.toFloat(), y.toFloat(), null)
        } catch (e: Exception) {
            print(e.printStackTrace())
        }
    }
}

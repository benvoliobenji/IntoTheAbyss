package com.example.intotheabyss.networking.packets

/**
 * The PlayerPacket is designed as the response to a ConnectionPacket upon the establishment of a connection to the
 * server. This contains all the information of the User's player that's ID was provided in the ConnectionPacket.
 * @constructor All of the user's player information.
 * @param playerID The user's unique ID.
 * @param playerName The user's display name.
 * @param floorNum The current floor the player is on.
 * @param posX The current x-position of the player.
 * @param posY The current y-position of the player.
 * @param health The current health of the player.
 *
 * @author Benjamin Vogel
 */
class PlayerPacket(val playerID: String, val playerName: String, val floorNum: Int,
                   val posX: Int, val posY: Int, val health: Int)
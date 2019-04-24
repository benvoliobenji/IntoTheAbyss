package com.example.intotheabyss.networking.packets

/**
 * The packet that gets sent to the server whenever the user's player moves. The client then sends the information to
 * the packet to notify other concurrent players and update monster logic.
 *
 * @constructor Given the correct parameters, sets the parameters as the payload for this packet.
 * @param playerID The ID of the player moving.
 * @param playerLocationFloor The current floor the player is on.
 * @param playerPositionX The x-position of the player.
 * @param playerPositionY The y-position of the player.
 *
 * @author Benjamin Vogel
 */
class PlayerLocationPacket(var playerID: String, var playerLocationFloor: Int,
                           var playerPositionX: Int, var playerPositionY: Int)
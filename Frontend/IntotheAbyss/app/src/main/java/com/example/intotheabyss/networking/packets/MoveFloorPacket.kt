package com.example.intotheabyss.networking.packets

/**
 * A packet designed to let the interface know when a player has moved from one floor to the next.
 * @constructor Provided the correct parameters, sets the parameters as the payload for the packet
 * @param playerID The player moving between floors.
 * @param floorNum The new floor the player is moving to.
 *
 * @author Benjamin Vogel
 */
class MoveFloorPacket(var playerID: String, var floorNum: Int)
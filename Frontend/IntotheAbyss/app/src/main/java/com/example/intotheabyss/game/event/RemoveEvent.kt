package com.example.intotheabyss.game.event

/**
 * A subclass of type Entity, which allows the network to kick players if the adminID is the correct Role.
 * @constructor Creates a KickEvent object.
 * @param adminID The administrator ID who wants to kick a Player.
 * @param kickedID The ID of the Player getting kicked.
 *
 * @author Benjamin Vogel
 */
class RemoveEvent(playerID: String) : Event(playerID, playerID, EventType.REMOVE)
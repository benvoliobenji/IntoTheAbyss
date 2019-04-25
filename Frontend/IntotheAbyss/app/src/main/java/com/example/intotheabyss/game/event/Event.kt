package com.example.intotheabyss.game.event


/**
 * An abstract class that will be used by the client to determine which actions to send to the server.
 * @constructor Constructs a default Event object.
 * @param performerID The ID of the Entity that performed the action.
 * @param performedID The ID of the Entity that the action was performed upon.
 * @param type The event type that occurred.
 * @author Benjamin Vogel
 */
abstract class Event(val performerID: String, val performedID: String, val type: EventType)
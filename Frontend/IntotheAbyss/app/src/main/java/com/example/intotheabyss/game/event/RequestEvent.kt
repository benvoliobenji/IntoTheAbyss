package com.example.intotheabyss.game.event

/**
 * A subclass of Event which allows the network to send a Request Packet to the server as well as inform the user of
 * incoming events.
 *
 * @constructor Creates a RequestEvent object.
 * @param requesterID The Player ID that is requesting another Player to join a group.
 * @param requesteeID The Player ID that is being requested to join a group.
 *
 * @author Benjamin Vogel
 */
class RequestEvent(requesterID: String, requesteeID: String) : Event(requesterID, requesteeID, EventType.REQUEST)
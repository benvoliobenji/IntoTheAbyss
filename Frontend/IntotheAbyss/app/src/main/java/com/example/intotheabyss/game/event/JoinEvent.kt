package com.example.intotheabyss.game.event

/**
 * A specific Join Event to denote the person's ID to join to.
 * @constructor Constructs a JoinEvent.
 * @param joiningID The person to join the group.
 * @param groupLeaderID The person who is the leader of the group.
 */
class JoinEvent(joiningID: String, groupLeaderID: String): Event(joiningID, groupLeaderID, EventType.JOIN)
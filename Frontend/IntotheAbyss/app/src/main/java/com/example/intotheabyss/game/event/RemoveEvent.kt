package com.example.intotheabyss.game.event

class RemoveEvent(playerID: String): Event(playerID, playerID, EventType.REMOVE)
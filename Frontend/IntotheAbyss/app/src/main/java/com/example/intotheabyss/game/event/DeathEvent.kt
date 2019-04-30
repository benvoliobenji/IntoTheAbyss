package com.example.intotheabyss.game.event

class DeathEvent(deadID: String): Event(deadID, deadID, EventType.DEATH)
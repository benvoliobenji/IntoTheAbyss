package com.example.intotheabyss.game.entity.entityaction

class Death(performerID: String, floor: Int): EntityAction(performerID, EntityActionType.DEATH, floor, performerID)
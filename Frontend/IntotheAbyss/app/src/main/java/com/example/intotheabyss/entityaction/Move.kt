package com.example.intotheabyss.entityaction


class Move(var location: Pair<Int, Int>, floorMovedTo: Int, private var attackPerformerID: String,
           private var attackFloor: Int, private var attackPayload: String):
    EntityAction(attackPerformerID, EntityActionType.MOVE, attackFloor, attackPayload)
package com.example.intotheabyss.entityaction

class Attack(var attackID: String, var dmg: Int,
             private var attackPerformerID: String, private var attackFloor: Int, private var attackPayload: String):
    EntityAction(attackPerformerID, EntityActionType.ATTACK, attackFloor, attackPayload)
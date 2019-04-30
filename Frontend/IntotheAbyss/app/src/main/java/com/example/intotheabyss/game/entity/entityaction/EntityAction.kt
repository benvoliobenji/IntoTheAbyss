package com.example.intotheabyss.game.entity.entityaction

open class EntityAction(var performerID: String = "",
                        var actionType: EntityActionType = EntityActionType.ADD,
                        var floor: Int = 0,
                        var payload: String = "")
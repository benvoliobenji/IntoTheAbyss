package com.example.intotheabyss.game.entity

abstract class Entity(var ID: String, var x: Int, var y: Int, var lastX: Int, var lastY: Int, var floor: Int,
                      var health: Int, var action: Int = 0, var type: EntityType)
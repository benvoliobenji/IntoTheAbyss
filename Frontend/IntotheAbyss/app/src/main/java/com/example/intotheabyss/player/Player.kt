package com.example.intotheabyss.player

import kotlin.random.Random

class Player {
    var playerID: String = Random.nextInt(0, 1000000).toString()
    var floorNumber: Int = 0
    var x: Int = 0
    var y: Int = 0
}
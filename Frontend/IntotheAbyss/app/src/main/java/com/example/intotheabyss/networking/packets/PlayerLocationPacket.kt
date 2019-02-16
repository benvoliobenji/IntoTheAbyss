package com.example.intotheabyss.networking.packets

import kotlin.random.Random

class PlayerLocationPacket(var playerID: String = Random.nextInt(0, 1000000).toString(),
                           var playerLocationFloor: Int = 0, var playerPositionX: Int = 0, var playerPositionY: Int = 0)
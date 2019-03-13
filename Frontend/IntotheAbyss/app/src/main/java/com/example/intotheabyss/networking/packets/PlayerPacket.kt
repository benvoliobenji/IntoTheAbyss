package com.example.intotheabyss.networking.packets

class PlayerPacket(val playerID: String, val playerName: String, val floorNum: Int,
                   val posX: Int, val posY: Int, val health: Int)
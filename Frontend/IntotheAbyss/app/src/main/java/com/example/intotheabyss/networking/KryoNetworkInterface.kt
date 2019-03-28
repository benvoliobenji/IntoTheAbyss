package com.example.intotheabyss.networking

import com.esotericsoftware.kryonet.Connection

interface KyroNetworkInterface {
    fun connect()
    fun received(c: Connection, o: Any)
    fun updatePosition(playerID: String, floor: Int, posX: Int, posY: Int)
    fun updateLevel(playerID: String, floor: Int)
}
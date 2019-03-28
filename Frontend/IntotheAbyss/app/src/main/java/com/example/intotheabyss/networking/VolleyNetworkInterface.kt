package com.example.intotheabyss.networking

import com.example.intotheabyss.game.GameState

interface VolleyNetworkInterface {
    fun createNewPlayer(playerName: String)
    fun retrievePlayerData(playerName: String)
    fun retrieveNewDungeonLevel(level: Int, network: Network)
}
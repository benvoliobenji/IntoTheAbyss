package com.example.intotheabyss.networking.volleynetwork

import com.example.intotheabyss.networking.Network

interface VolleyNetworkInterface {
    fun createNewPlayer(playerName: String)
    fun retrievePlayerData(playerName: String)
    fun retrieveNewDungeonLevel(level: Int, network: Network)
}
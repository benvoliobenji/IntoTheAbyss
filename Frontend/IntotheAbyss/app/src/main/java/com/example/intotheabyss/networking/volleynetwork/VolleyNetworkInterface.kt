package com.example.intotheabyss.networking.volleynetwork

import com.example.intotheabyss.networking.Network

interface VolleyNetworkInterface{
    fun retrievePlayerData(playerID: String, playerName: String)
    fun retrieveNewDungeonLevel(level: Int, network: Network)
}
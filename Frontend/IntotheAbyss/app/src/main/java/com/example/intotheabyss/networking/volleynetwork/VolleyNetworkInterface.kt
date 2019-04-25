package com.example.intotheabyss.networking.volleynetwork

import com.example.intotheabyss.networking.Network

/**
 * The Interface for VolleyNetwork that is implemented by VolleyNetwork. This is created to support dependency
 * injection throghout the project.
 * @author Benjamin Vogel
 */
interface VolleyNetworkInterface{
    fun retrievePlayerData(playerID: String, playerName: String)
    fun retrieveNewDungeonLevel(level: Int, network: Network)
}
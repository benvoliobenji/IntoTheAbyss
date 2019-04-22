package com.example.intotheabyss

import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.player.Player
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface

class FakeVolleyNetwork(var gameState: GameState): VolleyNetworkInterface {

    override fun retrievePlayerData(playerID: String, playerName: String) {
        gameState.myPlayer = Player(playerName, playerID, 3, 23, 12)
    }

    override fun retrieveNewDungeonLevel(level: Int, network: Network) {
        gameState.level = arrayOf(arrayOf(Wall(), Wall(), Wall(), Floor()),
            arrayOf(Wall(), Wall(), Wall(), Floor()))
    }
}
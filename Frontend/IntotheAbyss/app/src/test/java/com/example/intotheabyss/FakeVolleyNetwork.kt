package com.example.intotheabyss

import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.game.entity.player.Role
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface
import com.nhaarman.mockitokotlin2.isA

class FakeVolleyNetwork(var gameState: GameState): VolleyNetworkInterface {

    override fun retrievePlayerData(playerID: String, isAdmin: Boolean, playerName: String) {
        gameState.myPlayer = Player(playerName, playerID, 3, 23, 12)
        gameState.myPlayer.role = if (isAdmin) Role.ADMIN else Role.PLAYER
    }

    override fun retrieveNewDungeonLevel(level: Int, network: Network) {
        gameState.level = arrayOf(arrayOf(Wall(), Wall(), Wall(), Floor()),
            arrayOf(Wall(), Wall(), Wall(), Floor()))
    }
}
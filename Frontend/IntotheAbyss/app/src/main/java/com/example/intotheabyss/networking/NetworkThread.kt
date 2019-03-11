package com.example.intotheabyss.networking

import android.content.Context
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.packets.UpdateHandler
import java.io.File
import kotlin.random.Random

class NetworkRunnable(private val gameState: GameState, private val context: Context): Runnable {
    override fun run() {
        // This is the basic format for creating Volley requests, we just need to figure out what to send back and
        // forth in order for the server to understand what we're requesting.
        // This will eventually make it's way into the network thread and be handled there, especially with getting
        // new levels.

        val volleyNetwork = VolleyNetwork(context, gameState)

        val playerFile = File(context.filesDir, "PlayerID")
        if (playerFile.exists()) {
            val playerID = playerFile.readText()
            volleyNetwork.retrievePlayerData(playerID)
        } else {
            // Temporary name until we get the Google Account API linked
            val playerName = Random.nextInt(0, 1000000).toString()
            volleyNetwork.createNewPlayer(playerName)
        }

        val network = Network(gameState)
        network.connect()

        volleyNetwork.retrieveNewDungeonLevel(gameState.myPlayer.floorNumber)


        // Create new thread for updateHandler
        val updateHandler = UpdateHandler(network, volleyNetwork, gameState)
    }
}
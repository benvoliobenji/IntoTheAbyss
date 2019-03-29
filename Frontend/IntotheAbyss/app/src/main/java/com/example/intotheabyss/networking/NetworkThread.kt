package com.example.intotheabyss.networking

import android.content.Context
import com.example.intotheabyss.game.GameState
import java.io.File
import kotlin.random.Random

class NetworkRunnable(private val gameState: GameState, private val context: Context): Runnable {
    private var updateThread = Thread()

    override fun run() {
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

//        volleyNetwork.retrieveNewDungeonLevel(gameState.myPlayer.floorNumber)

        if(!updateThread.isAlive) {
            updateThread = Thread((UpdateRunnable(network, volleyNetwork, gameState)))
            updateThread.start()
        }
    }
}
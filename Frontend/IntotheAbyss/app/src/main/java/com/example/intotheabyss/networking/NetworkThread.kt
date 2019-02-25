package com.example.intotheabyss.networking

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.packets.UpdateHandler

class NetworkRunnable(private val gameState: GameState): Runnable {
    override fun run() {
        val network = Network(gameState)
        network.connect()
        val updateHandler = UpdateHandler(network, gameState)
    }
}
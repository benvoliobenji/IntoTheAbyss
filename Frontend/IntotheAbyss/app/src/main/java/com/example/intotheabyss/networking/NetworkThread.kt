package com.example.intotheabyss.networking

import com.example.intotheabyss.Game.GameState

class NetworkRunnable(private val gameState: GameState): Runnable {
    override fun run() {
        val network = Network(gameState)
        network.connect()
    }
}
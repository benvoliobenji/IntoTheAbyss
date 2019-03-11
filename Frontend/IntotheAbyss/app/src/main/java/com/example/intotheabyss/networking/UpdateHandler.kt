package com.example.intotheabyss.networking

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.VolleyNetwork
import java.lang.Thread.sleep

class UpdateRunnable(private val network: Network, private val volleyNetwork: VolleyNetwork,
                     private val gameState: GameState): Runnable {
    var posX: Int = gameState.myPlayer.x
    var posY: Int = gameState.myPlayer.y
    var floor: Int = gameState.myPlayer.floorNumber

    override fun run() {
        try {
            // We will probably want to change this at some point
            while (true) {
                sleep(25)
                if ((posX != gameState.myPlayer.x) or (posY != gameState.myPlayer.y)) {
                    posX = gameState.myPlayer.x
                    posY = gameState.myPlayer.y
                    network.updatePosition(gameState.myPlayer.playerID, floor, posX, posY)
                }

                if (floor != gameState.myPlayer.floorNumber) {
                    floor = gameState.myPlayer.floorNumber
                    volleyNetwork.retrieveNewDungeonLevel(floor)
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
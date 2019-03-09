package com.example.intotheabyss.networking

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.VolleyNetwork
import java.lang.Thread.sleep

class UpdateHandler(private val network: Network, private val volleyNetwork: VolleyNetwork,
                    private val gameState: GameState) {

    fun update() {

    }
}

class UpdateRunnable(private val network: Network, private val volleyNetwork: VolleyNetwork,
                     private val gameState: GameState): Runnable {
    var posX: Int = gameState.myPlayer.getX()
    var posY: Int = gameState.myPlayer.getY()
    var floor: Int = gameState.myPlayer.floorNumber

    override fun run() {
        try {
            // We will probably want to change this at some point
            while (true) {
                sleep(25)
                if ((posX != gameState.myPlayer.getX()) or (posY != gameState.myPlayer.getY())) {
                    posX = gameState.myPlayer.getX()
                    posY = gameState.myPlayer.getY()
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
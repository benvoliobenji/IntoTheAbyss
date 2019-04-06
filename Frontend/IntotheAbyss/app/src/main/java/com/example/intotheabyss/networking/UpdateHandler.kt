package com.example.intotheabyss.networking

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.updateverification.UpdateVerification
import com.example.intotheabyss.networking.updateverification.UpdateVerificationInterface
import com.example.intotheabyss.networking.updateverification.UpdateVerificationType
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface
import java.lang.Thread.sleep

class UpdateRunnable(private val network: Network, private val volleyNetworkInterface: VolleyNetworkInterface,
                     private val gameState: GameState): Runnable {
    val updateVerification: UpdateVerificationInterface =
        UpdateVerification(gameState.myPlayer.x, gameState.myPlayer.y, gameState.myPlayer.floorNumber)
    var updateType: UpdateVerificationType = UpdateVerificationType.NONE


    override fun run() {
        try {
            // We will probably want to change this at some point
            while (true) {
                sleep(500)
                updateType = updateVerification.verifyGameState(gameState, network, volleyNetworkInterface)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
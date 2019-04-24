package com.example.intotheabyss.networking

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.updateverification.UpdateVerification
import com.example.intotheabyss.networking.updateverification.UpdateVerificationInterface
import com.example.intotheabyss.networking.updateverification.UpdateVerificationType
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface
import java.lang.Thread.sleep

/**
 * An Update Thread that runs through a loop and calls UpdateVerification methods to make sure the server has the most
 * up-to-date information from the client.
 * @constructor Creates a new Update Thread thread.
 * @param network The instance of Kryonet network built by the client.
 * @param volleyNetworkInterface The VolleyNetworkInterface that is passed from the NetworkThread.
 * @param gameState The client's instance of GameState.
 * @author Benjamin Vogel
 */
class UpdateRunnable(private val network: Network, private val volleyNetworkInterface: VolleyNetworkInterface,
                     private val gameState: GameState): Runnable {
    val updateVerification: UpdateVerificationInterface =
        UpdateVerification(gameState.myPlayer.x, gameState.myPlayer.y, gameState.myPlayer.floorNumber)
    var updateType: UpdateVerificationType = UpdateVerificationType.NONE


    /**
     * Runs the UpdateThread.
     * While running, as long as there is no exceptions or interruption, call verifyGameState()
     * from UpdateVerificationInterface to verify GameState and notify the server.
     */
    override fun run() {
        try {
            // We will probably want to change this at some point
            while (true) {
                sleep(10)
                updateType = updateVerification.verifyGameState(gameState, network, volleyNetworkInterface)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
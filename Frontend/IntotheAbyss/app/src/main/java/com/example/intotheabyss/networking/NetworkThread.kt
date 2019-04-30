package com.example.intotheabyss.networking

import android.content.Context
import android.util.Log
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.lang.Thread.sleep

/**
 * A Network Thread that instantiates the Network and VolleyNetwork classes and creates/runs the UpdateThread.
 * This is created because Android does not want any networking on the main thread
 *
 * @constructor Create an instance of the Network Thread.
 * @param gameState The GameState object the client has created.
 * @param context The context the android application is currently running.
 * @author Benjamin Vogel
 */
class NetworkRunnable(private val gameState: GameState, private val isAdmin: Boolean,
                      private val context: Context): Runnable {
    private var updateThread = Thread()

    /**
     * Runs the NetworkThread as a new thread.
     *
     * Creates new instances of VolleyNetworkInterface and Network, connect to the server, retrieve the user's data
     * from the server, and then create and run the Update Thread.
     */
    override fun run() {
        try {
            val volleyNetworkInterface: VolleyNetworkInterface = VolleyNetwork(context, gameState)

            val account = GoogleSignIn.getLastSignedInAccount(context)
            val displayName = account?.displayName
            val personID = account?.id

            // Add/Retrieve data from server
            volleyNetworkInterface.retrievePlayerData(personID!!, isAdmin, displayName!!)

            sleep(10000)
            val network = Network(gameState)
            network.connect()

            volleyNetworkInterface.retrieveNewDungeonLevel(gameState.myPlayer.floor, network)
            sleep(10000)

            if (!updateThread.isAlive) {
                updateThread = Thread(UpdateRunnable(network, volleyNetworkInterface, gameState))
                updateThread.start()
            }
        } catch (e: InterruptedException) {
            updateThread.interrupt()
            return
        }
    }
}
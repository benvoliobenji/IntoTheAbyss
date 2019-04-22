package com.example.intotheabyss.networking

import android.content.Context
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface
import com.google.android.gms.auth.api.signin.GoogleSignIn

class NetworkRunnable(private val gameState: GameState, private val context: Context): Runnable {
    private var updateThread = Thread()

    override fun run() {
        val volleyNetworkInterface: VolleyNetworkInterface = VolleyNetwork(context, gameState)

        val account = GoogleSignIn.getLastSignedInAccount(context)
        val displayName = account?.displayName
        val personID = account?.id

        // Add/Retrieve data from server
        volleyNetworkInterface.retrievePlayerData(personID!!, displayName!!)

        val network = Network(gameState)
        network.connect()

//        volleyNetwork.retrieveNewDungeonLevel(gameState.myPlayer.floorNumber)

        if(!updateThread.isAlive) {
            updateThread = Thread(UpdateRunnable(network, volleyNetworkInterface, gameState))
            updateThread.start()
        }
    }
}
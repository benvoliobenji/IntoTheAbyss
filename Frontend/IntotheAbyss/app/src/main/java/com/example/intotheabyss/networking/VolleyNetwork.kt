package com.example.intotheabyss.networking

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.player.Player
import java.io.File

class VolleyNetwork(context: Context, gameState: GameState) {
    private var requestQueue: RequestQueue? = null
    private var gameState: GameState? = null
    private var context: Context? = null

    init {
        this.requestQueue = Volley.newRequestQueue(context)
        this.gameState = gameState
        this.context = context
    }

    fun createNewPlayer(playerName: String) {
        // Create new player URL
        val url = "cs309-ad-4.misc.iastate.edu:8080/players/add?username=$playerName"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val playerID = response.getString("playerID")
                val networkPlayerName = response.getString("username")
                val posX = response.getInt("posX")
                val posY = response.getInt("posY")
                val player = Player(networkPlayerName, playerID, 1, posX, posY)
                gameState?.myPlayer = player
                Log.i("PlayerRegistration", "Response: %s".format(player.toString()))

                // Save new playerID
                val file = File(context?.filesDir, "PlayerID")
                file.writeText(playerID)
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistration", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)

    }

    fun retrievePlayerData(playerName: String) {
        // Add the playerName to the url
        val url = "cs309-ad-4.misc.iastate.edu:8080/players/getPlayer?playerUUIDPassed=$playerName"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val playerID = response.getString("playerID")
                val networkPlayerName = response.getString("username")
                val posX = response.getInt("posX")
                val posY = response.getInt("posY")
                val player = Player(networkPlayerName, playerID, 1, posX, posY)
                gameState?.myPlayer = player
                Log.i("PlayerRegistration", "Response: %s".format(player.toString()))
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistration", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }

    fun retrieveNewDungeonLevel(level: Int) {
        val url = "cs309-ad-4.misc.iastate.edu:8080/levels/get?id=$level"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("DungeonLevel", "Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                Log.i("DungeonLevel", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }
}
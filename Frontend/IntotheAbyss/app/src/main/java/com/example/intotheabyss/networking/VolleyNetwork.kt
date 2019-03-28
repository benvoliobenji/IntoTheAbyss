package com.example.intotheabyss.networking

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.intotheabyss.dungeonassets.Stair
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.player.Player
import com.example.intotheabyss.utils.gridParse
import java.io.File

class VolleyNetwork(private var context: Context, private var gameState: GameState): VolleyNetworkInterface {
    private var requestQueue: RequestQueue? = null

    init {
        this.requestQueue = Volley.newRequestQueue(context)
    }

    override fun createNewPlayer(playerName: String) {
        // Create new player URL
        val url = "http://cs309-ad-4.misc.iastate.edu:8080/players/add?username=$playerName"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val playerID = response.getString("playerID")
                val networkPlayerName = response.getString("username")
                val posX = response.getInt("posX")
                val posY = response.getInt("posY")
                val player = Player(networkPlayerName, playerID, 0, posX, posY)
                gameState.myPlayer = player
                Log.i("PlayerRegistration", "Response: %s".format(player.toString()))

                // Save new playerID
                val file = File(context.filesDir, "PlayerID")
                file.writeText(playerID)
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistrationError", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)

    }

    override fun retrievePlayerData(playerName: String) {
        // Add the playerName to the url
        val url = "http://cs309-ad-4.misc.iastate.edu:8080/players/getPlayer?playerUUIDPassed=$playerName"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val playerID = response.getString("playerID")
                val networkPlayerName = response.getString("username")
                val posX = response.getInt("posX")
                val posY = response.getInt("posY")
                val player = Player(networkPlayerName, playerID, 0, posX, posY)
                gameState.myPlayer = player
                Log.i("PlayerRegistration", "Response: %s".format(player.toString()))
                Log.i("PlayerRegistrationX", posX.toString())
                Log.i("PlayerRegistrationY", posY.toString())
                Log.i("PlayerRegistrationID", playerID)
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistrationError", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }

    override fun retrieveNewDungeonLevel(level: Int, network: Network) {
        val url = "http://cs309-ad-4.misc.iastate.edu:8080/levels/get?id=$level"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val grid = response.getJSONArray("grid")
                val spawn = response.getJSONObject("spawn")
                val stairs = response.getJSONObject("stair")

                // Parse the JSONArray of JSONArrays into our 2D array of tiles, apply to gameState
                var levelGrid = gridParse(grid)

                // Placing the stairs
                val stairX = stairs.getInt("x")
                val stairY = stairs.getInt("y")
                levelGrid[stairY][stairX] = Stair()

                // Set the new level in gameState
                gameState.level = levelGrid

                // Placing the player
                val startX = spawn.getInt("x")
                val startY = spawn.getInt("y")
                gameState.myPlayer.x = startX
                gameState.myPlayer.y = startY

                network.updateLevel(gameState.myPlayer.playerID, gameState.myPlayer.floorNumber)

                gameState.loading = false
                Log.i("DungeonLevel", spawn.toString())
                Log.i("DungeonLevel", stairs.toString())
                Log.i("DungeonLevel", grid.toString())
            },
            Response.ErrorListener { error ->
                Log.i("DungeonLevelError", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }
}
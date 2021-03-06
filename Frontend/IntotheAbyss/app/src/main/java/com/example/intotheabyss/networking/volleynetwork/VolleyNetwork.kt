package com.example.intotheabyss.networking.volleynetwork

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.intotheabyss.dungeonassets.Stair
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.game.entity.player.Role
import com.example.intotheabyss.utils.gridParse
import java.io.File

/**
 * This class is designed to implement VolleyNetworkInterface and to query the server using Volley. These connections
 * are often used for files that are of larger size than what Kryonet can support and are also necessary for the client
 * and server to receive an ACK back based on the GET and PUT methods.
 * @constructor Constructs the VolleyNetwork object to query the server using HTTP/JSON requests over HTTP
 * @param context The context of the running android application.
 * @param gameState The GameState instance IntoTheAbyss is currently using.
 *
 * @author Benjamin Vogel
 */
class VolleyNetwork(private var context: Context, private var gameState: GameState):
    VolleyNetworkInterface {
    private var requestQueue: RequestQueue? = null

    init {
        this.requestQueue = Volley.newRequestQueue(context)
    }

    /**
     * Retrieves the User's player data through a JSONObjectRequest.
     *
     * Upon a response, sets the myPlayer object in GameState to the information that this method receives.
     *
     * @param playerID The ID of the user to retrieve their data from the server.
     * @param playerName The display name of the user who's data should be retrieved from the server.
     */
    override fun retrievePlayerData(playerID: String, isAdmin: Boolean, playerName: String) {
        Log.i("VolleyNetwork", "Sending request")
        val url = "http://cs309-ad-4.misc.iastate.edu:8080/players/getPlayer?playerUUIDPassed=$playerID" +
                "&playerNamePassed=$playerName&isAdmin=$isAdmin"
        Log.i("VolleyNetwork", url)
        Log.i("VolleyNetwork", "Request Sent")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("VolleyNetwork", response.toString())
                val networkPlayerID = response.getString("ID")
                val networkPlayerName = response.getString("username")
                val floor = response.getInt("floor")
                val posX = response.getInt("posX")
                val posY = response.getInt("posY")
                val health = response.getInt("health")
                val playerAdmin = response.getBoolean("isAdmin")
                val player = Player(playerName = networkPlayerName, playerID = networkPlayerID, health = health,
                    floorNumber = floor, xPos = posX, yPos = posY)

                player.role = if (playerAdmin) Role.ADMIN else Role.PLAYER

                gameState.myPlayer = player
                Log.i("PlayerRegistration", "Response: %s".format(player.toString()))
                Log.i("PlayerRegistrationX", posX.toString())
                Log.i("PlayerRegistrationY", posY.toString())
                Log.i("PlayerRegistrationID", player.ID)
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistrationError", "Error: %s".format(error.toString()))
            }
        )
        requestQueue?.add(jsonObjectRequest)
    }

    /**
     * Sends a JSONObjectRequest to get the 2D array of the dungeon layout of the given level.
     *
     * Upon a response, parse the grid with a utility method, place the stairs in the grid, and place the Player
     * object in GameState at the spawn location of the level.
     *
     * @param level The level number the User will be moving to.
     */
    override fun retrieveNewDungeonLevel(level: Int) {
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

                // Empty entities in the level to make sure that nothing gets carried over
                // gameState.entitiesInLevel.clear()

                // Add party members if player is in party
                if(gameState.myPlayer.party.isNotEmpty()) {
                    for (player in gameState.myPlayer.party) {
                        gameState.entitiesInLevel[player.ID] = player
                    }
                }

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
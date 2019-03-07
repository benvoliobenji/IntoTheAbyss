package com.example.intotheabyss.networking

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class VolleyNetwork(context: Context) {
    var requestQueue: RequestQueue? = null

    init {
        this.requestQueue = Volley.newRequestQueue(context)
    }

    fun createNewPlayer(playerName: String) {
        // Create new player URL
        val url = "/players/add?username=$playerName"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("PlayerRegistration", "Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistration", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)

    }

    fun retrievePlayerData(playerName: String) {
        // Add the playerName to the url
        val url = "/players/getPlayer?playerUUIDPassed=$playerName" // This will be the url for our player request
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("PlayerRegistration", "Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistration", "Error: %s".format(error.toString()))
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }

    fun retrieveNewDungeonLevel(level: Int) {
        val url = "/levels/get?id=#$level"
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
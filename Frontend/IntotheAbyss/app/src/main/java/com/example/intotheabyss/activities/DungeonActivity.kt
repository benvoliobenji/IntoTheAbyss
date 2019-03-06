package com.example.intotheabyss.activities

import android.content.Intent
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.intotheabyss.game.GameProcessingRunnable
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.R

import com.example.intotheabyss.game.GameView

import com.example.intotheabyss.networking.NetworkRunnable

class DungeonActivity : AppCompatActivity() {
    private var networkThread = Thread()
    private var gameProcessingThread = Thread()
    var gameState = GameState()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dungeon)
        var gameView = findViewById<GameView>(R.id.gView)

        gameView.setGameState(gameState)
        gameView.setOnTouchListener { gameView, event ->
            gameView.dispatchTouchEvent(event)
//            gameView.invalidate()
            true
        }

        // This is the basic format for creating Volley requests, we just need to figure out what to send back and
        // forth in order for the server to understand what we're requesting.
        // This will eventually make it's way into the network thread and be handled there, especially with getting
        // new levels.
        val requestQueue = Volley.newRequestQueue(this)
        val url = "" // This will be the url for our player request
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("PlayerRegistration", "Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                Log.i("PlayerRegistration", "Error: %s".format(error.toString()))
            }
        )

        requestQueue.add(jsonObjectRequest)

        if(!networkThread.isAlive) {
            networkThread = Thread(NetworkRunnable(gameState))
            networkThread.start()
        }

        if(!gameProcessingThread.isAlive) {
            gameProcessingThread = Thread(GameProcessingRunnable(gameState))
            gameProcessingThread.start()
        }

    }
}

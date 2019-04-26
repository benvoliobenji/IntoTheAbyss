package com.example.intotheabyss.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.R

import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.drawplayer.gameView

import com.example.intotheabyss.networking.NetworkRunnable
import kotlinx.android.synthetic.main.activity_dungeon.*

class DungeonActivity : AppCompatActivity() {
    private var networkThread = Thread()
    var gameState = GameState()
    var debug = false


    @SuppressLint("ClickableViewAccessibility") //This is for blind people accessability- sorry blind people
    override fun onCreate(savedInstanceState: Bundle?) {
        debug = intent.getBooleanExtra("debug", false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dungeon)
        var gameView = findViewById<GameView>(R.id.gView)

        gameView.gameState = gameState
        gameView.debug = debug

        if (debug) {
            debugString.setText(R.string.debug_string)
        } else  {
            debugString.setText(R.string.not_debug_string)
        }

        gameView.setOnTouchListener { _, event ->
            gameView.dispatchTouchEvent(event)
//            gameView.invalidate()
            true
        }

        if(!networkThread.isAlive) {
            // Add networkThread.IsBackground = true?
            networkThread = Thread(NetworkRunnable(gameState, this))
            networkThread.start()
        }

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (gameView!!.dead)    {
            val intent = Intent(this, DeathActivity::class.java)
            intent.putExtra("level", gameState.level)
            startActivity(intent)
        }
    }
}

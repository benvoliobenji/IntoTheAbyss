package com.example.intotheabyss.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.R

import com.example.intotheabyss.game.GameView

import com.example.intotheabyss.networking.NetworkRunnable
import kotlinx.android.synthetic.main.activity_dungeon.*
import kotlinx.android.synthetic.main.activity_main.*

class DungeonActivity : AppCompatActivity() {
    private var networkThread = Thread()
    var gameState = GameState()
    var admin = false
    var gameView: GameView? = null


    @SuppressLint("ClickableViewAccessibility") //This is for blind people accessability- sorry blind people
    override fun onCreate(savedInstanceState: Bundle?) {
        admin = intent.getBooleanExtra("admin", false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dungeon)
        var gameView = findViewById<GameView>(R.id.gView)
        this.gameView = gameView

        gameView.gameState = gameState
        gameView.debug = false

        if (false) {
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
            networkThread = Thread(NetworkRunnable(gameState, admin, this))
            networkThread.start()
        }

        if (gameView!!.deathActivity)   {
            val intent = Intent(this, DeathActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (gameView!!.deathActivity) {
            val intent = Intent(this, DeathActivity::class.java)
            intent.putExtra("level", gameState.level)
            startActivity(intent)
        }
    }

}

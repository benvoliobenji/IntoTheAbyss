package com.example.intotheabyss.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.R

import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.event.DisconnectEvent

import com.example.intotheabyss.networking.NetworkRunnable
import kotlinx.android.synthetic.main.activity_dungeon.*

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
        val gameView = findViewById<GameView>(R.id.gView)
        this.gameView = gameView

        gameView.gameState = gameState
        gameView.debug = false

        debugString.setText(R.string.not_debug_string)

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
            // intent.putExtra("level", gameState.level)
            startActivity(intent)
        }
        if (gameView!!.kicked)  {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop()   {
        val disc = DisconnectEvent()
        gameState.eventQueue.add(disc)

        while (!gameState.eventQueue.isEmpty()) {
            Thread.sleep(5)
        }
        networkThread.interrupt()
        super.onStop()
    }
}

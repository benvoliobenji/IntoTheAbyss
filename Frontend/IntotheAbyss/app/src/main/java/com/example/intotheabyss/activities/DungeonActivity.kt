package com.example.intotheabyss.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        if(!networkThread.isAlive) {
            // Add networkThread.IsBackground = true?
            networkThread = Thread(NetworkRunnable(gameState, this))
            networkThread.start()
        }

    }
}

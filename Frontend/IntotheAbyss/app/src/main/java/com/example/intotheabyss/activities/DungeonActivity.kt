package com.example.intotheabyss.activities

import android.content.Intent
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
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

package com.example.intotheabyss.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.intotheabyss.game.GameProcessingRunnable
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.R
import com.example.intotheabyss.networking.NetworkRunnable

class DungeonActivity : AppCompatActivity() {
    private var networkThread = Thread()
    private var gameProcessingThread = Thread()
    var gameState = GameState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dungeon)

        if(!networkThread.isAlive) {
            networkThread = Thread(NetworkRunnable(gameState))
            networkThread.start()
        }

        if(!gameProcessingThread.isAlive) {
            gameProcessingThread = Thread(GameProcessingRunnable(gameState))
            gameProcessingThread.start()
        }

        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }

    }
}

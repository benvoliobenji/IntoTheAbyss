package com.example.intotheabyss.activities

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.intotheabyss.R
import com.example.intotheabyss.networking.NetworkRunnable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ring = MediaPlayer.create(this, R.raw.car_sound)

        val play = findViewById<Button>(R.id.playButton)
        play.setOnClickListener {
            val intent = Intent(this, DungeonActivity::class.java)
            startActivity(intent)
            ring.start()
        }

        val settings = findViewById<Button>(R.id.settingsButton)
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }


    }
}

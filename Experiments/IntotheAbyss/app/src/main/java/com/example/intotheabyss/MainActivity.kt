package com.example.intotheabyss

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ring = MediaPlayer.create(this, R.raw.car_sound)

        val play = findViewById(R.id.playButton) as Button
        play.setOnClickListener {
            val intent = Intent(this, dungeonActivity::class.java)
                startActivity(intent)
            ring.start()
        }

        val settings = findViewById(R.id.settingsButton) as Button
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
        }


    }
}

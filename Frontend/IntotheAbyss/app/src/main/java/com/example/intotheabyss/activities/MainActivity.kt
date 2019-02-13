package com.example.intotheabyss.activities

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import com.example.intotheabyss.R
import com.example.intotheabyss.networking.Network

class MainActivity : AppCompatActivity() {

    private var network: Network = Network()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val play = findViewById<Button>(R.id.playButton)
        play.setOnClickListener {
            val intent = Intent(this, DungeonActivity::class.java)
            startActivity(intent)

            //Proposed fix (For network errors maybe?)
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

//            this.network.connect()
        }

        val settings = findViewById<Button>(R.id.settingsButton)
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
        }


    }
}

package com.example.intotheabyss

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class dungeonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dungeon)

        val returnButton = findViewById(R.id.returnButton) as Button
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }
    }
}

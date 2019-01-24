package com.example.hellurworld

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val helloText: TextView = findViewById(R.id.text)

        //Hellur button goes to Hellur.kt
        val b1 = findViewById(R.id.button1) as Button
        b1.setOnClickListener{
            val intent = Intent(this, Hellur::class.java)
            startActivity(intent)
        }

        val b2 = findViewById(R.id.button2) as Button
        b2.setOnClickListener{
            helloText.text = getString(R.string.wooooo)
        }
    }
}

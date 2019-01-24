package com.example.hellurworld

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch

class Hellur : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hellur)

        val retButton = findViewById(R.id.returnButton) as (Button)

        val switch_button = findViewById(R.id.fontSwitch) as (Switch)
        switch_button.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                retButton.setBackgroundColor(Color.MAGENTA)
            } else {
                // The switch is disabled
                retButton.setBackgroundColor(Color.GREEN)
            }
        }

        retButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}

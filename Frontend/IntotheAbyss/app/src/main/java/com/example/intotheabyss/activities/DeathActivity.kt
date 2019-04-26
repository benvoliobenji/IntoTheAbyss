package com.example.intotheabyss.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.intotheabyss.R

import kotlinx.android.synthetic.main.activity_death.*

class DeathActivity : AppCompatActivity() {
    private var lvl = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_death)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lvl = intent.getIntExtra("level", 0)
    }

}

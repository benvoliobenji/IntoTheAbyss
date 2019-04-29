package com.example.intotheabyss.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.intotheabyss.R

import kotlinx.android.synthetic.main.activity_death.*

class DeathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var level = intent.getIntExtra("level", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_death)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

}

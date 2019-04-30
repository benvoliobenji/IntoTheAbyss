package com.example.intotheabyss.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import com.example.intotheabyss.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    var RC_SIGN_IN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings = findViewById<Button>(R.id.settingsButton)
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val adminToggle = findViewById<Switch>(R.id.adminToggle)

        // Google Sign-In
        val signInButton = findViewById<Button>(R.id.playButton)
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInButton.setOnClickListener {signIn()}
    }

    fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            var account: GoogleSignInAccount? = completedTask.result
            val intent = Intent(this, DungeonActivity::class.java)
            intent.putExtra("admin", adminToggle.isChecked)
            startActivity(intent)
        } catch(e: ApiException) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
        }
    }

//    override fun onStart() {
//        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
//        if (account != null) {
//            var intent = Intent(this, DungeonActivity::class.java)
//            intent.putExtra("debug", debugToggle.isChecked)
//            startActivity(intent)
//        }
//        super.onStart()
//    }
}

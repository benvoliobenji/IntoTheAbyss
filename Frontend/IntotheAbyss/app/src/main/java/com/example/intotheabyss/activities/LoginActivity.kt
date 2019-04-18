package com.example.intotheabyss.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.intotheabyss.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity: AppCompatActivity() {
    var mGoogleSignInClient: GoogleSignInClient? = null
    var RC_SIGN_IN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var signInButton = findViewById<Button>(R.id.sign_in_button)
        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build()

        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInButton.setOnClickListener {signIn()}
    }

    fun signIn() {
        var signInIntent = mGoogleSignInClient?.signInIntent
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
            startActivity(Intent(this, DungeonActivity::class.java))
        } catch(e: ApiException) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            startActivity(Intent(this, DungeonActivity::class.java))
        }
        super.onStart()
    }
}
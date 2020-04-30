package com.fruktorum.ftauthsample.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (FTAuth.getInstance().getAuthToken() != "") {
            startActivity(Intent(this, MainActivity::class.java))
        }
        FTAuth.getInstance().onLoginSuccess = {
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
        FTAuth.getInstance().onLoginFailure = {
            Toast.makeText(this, it.localizedMessage!!, Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        FTAuth.getInstance().onStop()
    }
}

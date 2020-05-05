package com.fruktorum.ftauthsample.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.data.auth.TypeElement
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.main.MainActivity

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        FTAuth.getInstance().requiredElements =
            listOf(
                TypeElement.EMAIL,
                TypeElement.PASSWORD,
                TypeElement.CONFIRM_PASSWORD,
                TypeElement.FIRST_NAME,
                TypeElement.LAST_NAME
            )

        FTAuth.getInstance().onRegistrationSuccess = {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
        FTAuth.getInstance().onRegistrationFailure = {
            Toast.makeText(this, it.localizedMessage!!, Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        FTAuth.getInstance().onStop()
    }
}

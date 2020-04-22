package com.fruktorum.ftauthsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fruktorum.ftauth.FTAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FTAuth.getInstance().onLoginSuccess = {
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
        }
        btn_login.setOnClickListener {
            FTAuth.getInstance().login(email_input_field, password_input_field)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        FTAuth.getInstance().onStop()
    }
}

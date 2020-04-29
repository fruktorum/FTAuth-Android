package com.fruktorum.ftauthsample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FTAuth.getInstance().onLogOutSuccess = {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btn_log_out.setOnClickListener {
            FTAuth.getInstance().logOut()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        FTAuth.getInstance().onStop()
    }
}

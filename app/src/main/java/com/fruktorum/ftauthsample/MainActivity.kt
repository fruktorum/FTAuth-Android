package com.fruktorum.ftauthsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fruktorum.ftauth.FTAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FTAuth.Companion.Builder(this).setServerUrl("").build()

    }
}

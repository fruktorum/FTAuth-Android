package com.fruktorum.ftauthsample.application

import android.app.Application
import com.fruktorum.ftauth.FTAuth


class FTAuthSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FTAuth.Companion.Builder(this).setServerUrl("https://8af269c3.ngrok.io").build()
    }
}

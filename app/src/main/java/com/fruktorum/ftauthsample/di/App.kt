package com.fruktorum.ftauthsample.di

import com.fruktorum.ftauth.FTAuth
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        super.onCreate()
        FTAuth.Companion.Builder(this).setServerUrl("http://204.155.154.249:3003").build()
    }
}
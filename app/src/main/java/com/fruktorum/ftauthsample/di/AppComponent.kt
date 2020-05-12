package com.fruktorum.ftauthsample.di

import android.app.Application
import com.fruktorum.ftauthsample.di.modules.ActivitiesBindingModule
import com.fruktorum.ftauthsample.di.modules.AppModule
import com.fruktorum.ftauthsample.di.modules.NavigationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NavigationModule::class,
        ActivitiesBindingModule::class,
        AppModule::class
    ]
)

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
package com.fruktorum.ftauthsample.di.modules


import com.fruktorum.ftauthsample.ui.main.MainActivity
import com.fruktorum.ftauthsample.ui.main.module.MainFragmentsBindingModule
import com.fruktorum.ftauthsample.util.annotations.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [MainFragmentsBindingModule::class]
    )
    abstract fun bindMainActivity(): MainActivity


}
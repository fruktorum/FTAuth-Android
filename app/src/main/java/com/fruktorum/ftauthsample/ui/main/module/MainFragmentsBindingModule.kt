package com.fruktorum.ftauthsample.ui.main.module

import com.fruktorum.ftauthsample.ui.login.LoginFragment
import com.fruktorum.ftauthsample.ui.main.LogOutFragment
import com.fruktorum.ftauthsample.ui.register.RegistrationFragment
import com.fruktorum.ftauthsample.util.annotations.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentsBindingModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindLogOutFragment(): LogOutFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindRegistrationFragment(): RegistrationFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment
}
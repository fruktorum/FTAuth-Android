package com.fruktorum.ftauthsample.ui

import androidx.fragment.app.Fragment
import com.fruktorum.ftauthsample.ui.login.LoginFragment
import com.fruktorum.ftauthsample.ui.main.LogOutFragment
import com.fruktorum.ftauthsample.ui.register.RegistrationFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object LogOutScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return LogOutFragment()
        }
    }

    object LoginScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return LoginFragment()
        }
    }

    object RegistrationScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return RegistrationFragment()
        }
    }
}
package com.fruktorum.ftauthsample.ui.main

import android.os.Bundle
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.ui.base.BaseActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val navigator = SupportAppNavigator(this, R.id.container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override val layoutRes: Int
        get() = R.layout.activity_main


    override fun renderView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            if (FTAuth.getInstance().getSessionToken() != "") {
                router.newRootScreen(Screens.LogOutScreen)
            } else router.newRootScreen(Screens.LoginScreen)
        }
    }


    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        FTAuth.getInstance().onStop()
    }
}

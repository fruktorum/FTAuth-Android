package com.fruktorum.ftauthsample.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.ui.base.BaseFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LogOutFragment : BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_log_out

    @Inject
    lateinit var router: Router


    override fun renderView(view: View, savedInstanceState: Bundle?) {
        FTAuth.getInstance().onLogOutSuccess = {
            router.newRootScreen(Screens.LoginScreen)
        }
        FTAuth.getInstance().onLogOutFailure = {
            Log.d("Error", it.message.toString())
            Unit
        }
    }

}

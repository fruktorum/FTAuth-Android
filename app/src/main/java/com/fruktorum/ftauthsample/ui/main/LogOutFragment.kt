package com.fruktorum.ftauthsample.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.ui.base.BaseFragment
import com.fruktorum.ftauthsample.ui.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_log_out.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LogOutFragment : BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_log_out

    @Inject
    lateinit var router: Router


    override fun renderView(view: View, savedInstanceState: Bundle?) {
        FTAuth.getInstance().onLogOutSuccess = {
            startActivity(Intent(requireContext(), LoginFragment::class.java))
        }
        btn_log_out.setOnClickListener {
            FTAuth.getInstance().logOut()
            router.newRootScreen(Screens.LoginScreen)
        }
    }

}

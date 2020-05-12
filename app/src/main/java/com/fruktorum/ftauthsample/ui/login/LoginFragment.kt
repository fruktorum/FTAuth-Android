package com.fruktorum.ftauthsample.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_login

    @Inject
    lateinit var router: Router

    override fun renderView(view: View, savedInstanceState: Bundle?) {
        FTAuth.getInstance().onLoginSuccess = {
            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_LONG).show()
            router.newRootScreen(Screens.LogOutScreen)

        }
        FTAuth.getInstance().onLoginFailure = {
            Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_LONG).show()
        }

        btn_register.setOnClickListener {
            router.navigateTo(Screens.RegistrationScreen)

        }
    }

}

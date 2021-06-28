package com.fruktorum.ftauthsample.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.data.auth.TypeElement
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.ui.base.BaseFragment
import com.fruktorum.ftauthsample.util.extensions.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RegistrationFragment : BaseFragment() {

    @Inject
    lateinit var router: Router

    override val layoutRes: Int
        get() = R.layout.fragment_registration


    override fun renderView(view: View, savedInstanceState: Bundle?) {
        FTAuth.getInstance().requiredElements =
            listOf(
                TypeElement.EMAIL,
                TypeElement.PASSWORD,
                TypeElement.CONFIRM_PASSWORD,
                TypeElement.NAME,
                TypeElement.LAST_NAME,
                TypeElement.FIRST_NAME,
                TypeElement.ACCEPT
            )

        FTAuth.getInstance().onRegistrationSuccess = {
            Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_LONG).show()
            router.newRootScreen(Screens.LogOutScreen)

        }
        FTAuth.getInstance().onRegistrationFailure = {
            Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_LONG).show()
        }

        root_view.setOnClickListener {
            hideKeyboard()
        }

    }

}

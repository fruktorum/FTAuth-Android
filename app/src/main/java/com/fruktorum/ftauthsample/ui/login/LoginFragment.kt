package com.fruktorum.ftauthsample.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.databinding.FragmentLoginBinding
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.util.extensions.hideKeyboard
import dagger.android.support.AndroidSupportInjection
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FTAuth.getInstance().onLoginSuccess = {
            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_LONG).show()
            router.newRootScreen(Screens.LogOutScreen)

        }
        FTAuth.getInstance().onLoginFailure = {
            Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_LONG).show()
        }

        binding.btnRegister.setOnClickListener {
            router.navigateTo(Screens.RegistrationScreen)

        }

        binding.emailInputField.setInputFieldStyle(R.style.InputField)
        binding.passwordInputField.setInputFieldStyle(R.style.InputField)


        binding.rootView.setOnClickListener {
            hideKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

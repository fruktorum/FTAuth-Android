package com.fruktorum.ftauthsample.ui.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.data.auth.TypeElement
import com.fruktorum.ftauth.data.phoneNumber.PhoneMask
import com.fruktorum.ftauthsample.R
import com.fruktorum.ftauthsample.databinding.FragmentRegistrationBinding
import com.fruktorum.ftauthsample.ui.Screens
import com.fruktorum.ftauthsample.util.extensions.hideKeyboard
import dagger.android.support.AndroidSupportInjection
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    @Inject
    lateinit var router: Router

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FTAuth.getInstance().requiredElements =
            listOf(
                TypeElement.EMAIL,
                TypeElement.PASSWORD,
                TypeElement.CONFIRM_PASSWORD,
                TypeElement.NAME,
                TypeElement.LAST_NAME,
                TypeElement.FIRST_NAME,
                TypeElement.PHONE,
                TypeElement.ACCEPT
            )

        binding.phoneInputField.phoneMask = PhoneMask.PLUS

        FTAuth.getInstance().onRegistrationSuccess = {
            Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_LONG).show()
            router.newRootScreen(Screens.LogOutScreen)

        }
        FTAuth.getInstance().onRegistrationFailure = {
            Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_LONG).show()
        }

        binding.rootView.setOnClickListener {
            hideKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

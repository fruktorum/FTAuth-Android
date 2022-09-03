package com.fruktorum.ftauth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.annotation.ColorRes
import com.fruktorum.ftauth.customUI.auth.FTAuthEmailInputField
import com.fruktorum.ftauth.customUI.auth.FTAuthPasswordInputField
import com.fruktorum.ftauth.customUI.registration.FTCheckBoxAcceptanceOfTerms
import com.fruktorum.ftauth.customUI.registration.FTRegistrationConfirmPasswordInputField
import com.fruktorum.ftauth.customUI.registration.FTRegistrationEmailInputField
import com.fruktorum.ftauth.customUI.registration.FTRegistrationFirstNameInputField
import com.fruktorum.ftauth.customUI.registration.FTRegistrationLastNameInputField
import com.fruktorum.ftauth.customUI.registration.FTRegistrationNameInputField
import com.fruktorum.ftauth.customUI.registration.FTRegistrationPasswordInputField
import com.fruktorum.ftauth.customUI.registration.FTRegistrationPhoneNumberInputField
import com.fruktorum.ftauth.customUI.webView.WebViewActivity
import com.fruktorum.ftauth.data.ErrorHandler
import com.fruktorum.ftauth.data.auth.TypeElement
import com.fruktorum.ftauth.data.auth.dataModel.RegisterUserDataModel
import com.fruktorum.ftauth.data.base.MethodType
import com.fruktorum.ftauth.network.AuthLocalDataProvider
import com.fruktorum.ftauth.network.RetrofitHelper
import com.fruktorum.ftauth.network.repository.AuthRepository
import com.fruktorum.ftauth.network.usecase.GetFacebookSignInUrlUseCase
import com.fruktorum.ftauth.network.usecase.GetGoogleSignInUrlUseCase
import com.fruktorum.ftauth.network.usecase.LogOutUserUseCase
import com.fruktorum.ftauth.network.usecase.LoginUserUseCase
import com.fruktorum.ftauth.network.usecase.RegisterUserUseCase
import com.fruktorum.ftauth.util.constants.PrefsConstants
import com.fruktorum.ftauth.util.extensions.async
import io.reactivex.disposables.CompositeDisposable

class FTAuth {

    var onLoginSuccess: (() -> Unit?)? = null
    var onLoginFailure: ((Throwable) -> Unit?)? = null

    var onRegistrationSuccess: (() -> Unit?)? = null
    var onRegistrationFailure: ((Throwable) -> Unit?)? = null

    var onLogOutSuccess: (() -> Unit?)? = null
    var onLogOutFailure: ((Throwable) -> Unit?)? = null

    var requiredElements = listOf<TypeElement>()

    var additionalRegistrationPayload: HashMap<String, Any>? = null

    var serverUrl: String? = null

    var disposables = CompositeDisposable()

    private val errorHandler = ErrorHandler()

    private var authRepository: AuthRepository? = null

    fun login() {
        authEmailInputField?.validate()
        authPasswordInputField?.validate()
        if (authEmailInputField?.isEmailValid == false || authPasswordInputField?.isPasswordValid == false) return
        val uc = LoginUserUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable(authEmailInputField!!.value, authPasswordInputField!!.value)
                .flatMap {
                    authRepository!!.getSessionTokenAsync()
                }
                .async()
                .subscribe({
                    onLoginSuccess?.invoke()
                }, {
                    handleError(it, MethodType.AUTH)
                    onLoginFailure?.invoke(it)
                })
        )

    }

    fun registration() {
        if (checkValidAllElements()) {
            val uc = RegisterUserUseCase(instance!!.authRepository!!)
            val payload = hashMapOf<String, Any?>()
            payload["first_name"] = registerFirstNameInputField?.value
            payload["last_name"] = registerLastNameInputField?.value
            payload["username"] = registerNameInputField?.value
            if (additionalRegistrationPayload != null) {
                additionalRegistrationPayload!!.forEach {
                    payload[it.key] = it.value
                }
            }
            disposables.add(
                uc.createObservable(
                    RegisterUserDataModel(
                        registerEmailInputField!!.value,
                        registerPasswordInputField!!.value,
                        payload
                    )
                )
                    .async()
                    .subscribe({
                        onRegistrationSuccess?.invoke()
                    }, {
                        errorHandler.handle(it, MethodType.REGISTRATION)
                        onRegistrationFailure?.invoke(it)
                    })
            )
        }
    }

    fun loginByGoogle(context: Context) {
        val uc = GetGoogleSignInUrlUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable()
                .async()
                .subscribe({
                    context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.WEB_VIEW_URL, it.url)
                    })
                }, {
                    errorHandler.handle(it, MethodType.AUTH_GOOGLE)
                    onLoginFailure?.invoke(it)
                })
        )
    }

    fun loginByFacebook(context: Context) {
        val uc = GetFacebookSignInUrlUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable()
                .async()
                .subscribe({
                    context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.WEB_VIEW_URL, it.url)
                    })
                }, {
                    errorHandler.handle(it, MethodType.AUTH_FACEBOOK)
                    onLoginFailure?.invoke(it)
                })
        )
    }

    fun logOut() {
        val uc = LogOutUserUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable()
                .async()
                .subscribe({
                    onLogOutSuccess?.invoke()
                }, {
                    errorHandler.handle(it, MethodType.LOGOUT)
                    onLogOutFailure?.invoke(it)
                })
        )
    }

    fun getSessionToken() = instance!!.authRepository!!.getSessionToken()

    fun getProviderToken() = instance!!.authRepository!!.getProviderToken()

    fun setSessionToken(sessionToken: String) =
        instance?.authRepository?.setSessionToken(sessionToken)

    fun setProviderToken(providerToken: String) =
        instance?.authRepository?.setProviderToken(providerToken)

    fun onStop() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }

    private fun checkValidAllElements(): Boolean {
        var isValid = true
        getInstance().requiredElements.forEach {
            when (it) {
                TypeElement.NONE -> return@forEach
                TypeElement.FIRST_NAME -> {
                    registerFirstNameInputField?.validate()
                    isValid =
                        if (isValid) registerFirstNameInputField?.isFirstNameValid
                            ?: false else isValid
                }
                TypeElement.LAST_NAME -> {
                    registerLastNameInputField?.validate()
                    isValid =
                        if (isValid) registerLastNameInputField?.isLastNameValid
                            ?: false else isValid
                }
                TypeElement.PASSWORD -> {
                    registerPasswordInputField?.validate()
                    isValid =
                        if (isValid) registerPasswordInputField?.isPasswordValid
                            ?: false else isValid
                }
                TypeElement.CONFIRM_PASSWORD -> {
                    registerConfirmPasswordInputField?.validate()
                    isValid =
                        if (isValid) registerConfirmPasswordInputField?.isPasswordValid
                            ?: false else isValid
                }
                TypeElement.EMAIL -> {
                    registerEmailInputField?.validate()
                    isValid =
                        if (isValid) registerEmailInputField?.isEmailValid
                            ?: false else isValid
                }
                TypeElement.NAME -> {
                    registerNameInputField?.validate()
                    isValid =
                        if (isValid) registerNameInputField?.isNameValid
                            ?: false else isValid
                }
                TypeElement.PHONE -> {
                    registerPhoneNumberInputField?.validate()
                    isValid =
                        if (isValid) registerPhoneNumberInputField?.isPhoneValid
                            ?: false else isValid
                }
                TypeElement.ACCEPT -> isValid =
                    if (isValid) checkBoxAcceptanceOfTerms?.isChecked
                        ?: false else isValid
            }
        }
        return isValid
    }

    private fun handleError(throwable: Throwable, methodType: MethodType) {
        errorHandler.handle(throwable, methodType)
    }

    companion object {
        const val TAG = "FTAuth"
        private const val FT_AUTH_GET_INSTANCE_ERROR_MSG =
            "FTAuth was not initialized properly. Use FTAuth.Builder to init library."
        private const val FT_AUTH_SERVER_URL_ERROR = "FTAuth server url is null or empty."

        @ColorRes
        var errorMessageColor: Int = R.color.colorError

        // Custom UI fields
        @SuppressLint("StaticFieldLeak")
        var authEmailInputField: FTAuthEmailInputField? = null
        @SuppressLint("StaticFieldLeak")
        var authPasswordInputField: FTAuthPasswordInputField? = null

        @SuppressLint("StaticFieldLeak")
        var registerEmailInputField: FTRegistrationEmailInputField? = null
        @SuppressLint("StaticFieldLeak")
        var registerPasswordInputField: FTRegistrationPasswordInputField? = null
        @SuppressLint("StaticFieldLeak")
        var registerConfirmPasswordInputField: FTRegistrationConfirmPasswordInputField? = null
        @SuppressLint("StaticFieldLeak")
        var registerFirstNameInputField: FTRegistrationFirstNameInputField? = null
        @SuppressLint("StaticFieldLeak")
        var registerLastNameInputField: FTRegistrationLastNameInputField? = null
        @SuppressLint("StaticFieldLeak")
        var registerNameInputField: FTRegistrationNameInputField? = null
        @SuppressLint("StaticFieldLeak")
        var registerPhoneNumberInputField: FTRegistrationPhoneNumberInputField? = null
        var checkBoxAcceptanceOfTerms: FTCheckBoxAcceptanceOfTerms? = null

        private var instance: FTAuth? = null

        @Synchronized
        @JvmStatic
        fun getInstance(): FTAuth {
            return if (instance != null) {
                instance!!
            } else {
                throw IllegalStateException(FT_AUTH_GET_INSTANCE_ERROR_MSG)
            }
        }

        class Builder(
            private var context: Context
        ) {

            init {
                instance = FTAuth()
            }

            @Throws(IllegalStateException::class)
            fun build() {
                if (instance!!.serverUrl.isNullOrEmpty()) {
                    throw IllegalStateException(FT_AUTH_SERVER_URL_ERROR)
                }
                val retrofit = RetrofitHelper(instance!!.serverUrl!!)
                instance!!.authRepository =
                    AuthRepository(
                        retrofit.authApi,
                        AuthLocalDataProvider(
                            context.getSharedPreferences(
                                context.packageName!! + PrefsConstants.APP_NAME,
                                Context.MODE_PRIVATE
                            )
                        )
                    )
            }

            fun setServerUrl(serverUrl: String?): Builder {
                instance!!.serverUrl = serverUrl
                return this
            }
        }
    }
}

package com.fruktorum.ftauth

import android.content.Context
import com.fruktorum.ftauth.customUI.auth.FTAuthEmailInputField
import com.fruktorum.ftauth.customUI.auth.FTAuthPasswordInputField
import com.fruktorum.ftauth.customUI.registration.*
import com.fruktorum.ftauth.data.auth.TypeElement
import com.fruktorum.ftauth.data.auth.dataModel.RegisterUserDataModel
import com.fruktorum.ftauth.network.AuthLocalDataProvider
import com.fruktorum.ftauth.network.RetrofitHelper
import com.fruktorum.ftauth.network.repository.AuthRepository
import com.fruktorum.ftauth.network.usecase.LogOutUserUseCase
import com.fruktorum.ftauth.network.usecase.LoginUserUseCase
import com.fruktorum.ftauth.network.usecase.RegisterUserUseCase
import com.fruktorum.ftauth.util.constants.PrefsConstants
import com.fruktorum.ftauth.util.extensions.async
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class FTAuth {

    private var authRepository: AuthRepository? = null

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

    companion object {
        private var instance: FTAuth? = null

        //Custom UI fields
        var authEmailInputField: FTAuthEmailInputField? = null
        var authPasswordInputField: FTAuthPasswordInputField? = null

        var registerEmailInputField: FTRegistrationEmailInputField? = null
        var registerPasswordInputField: FTRegistrationPasswordInputField? = null
        var registerConfirmPasswordInputField: FTRegistrationConfirmPasswordInputField? = null
        var registerFirstNameInputField: FTRegistrationFirstNameInputField? = null
        var registerLastNameInputField: FTRegistrationLastNameInputField? = null


        @Synchronized
        @JvmStatic
        fun getInstance(): FTAuth {
            return if (instance != null) instance!! else throw IllegalStateException(
                "FTAuth was not initialized properly. Use FTAuth.Builder to init library."
            )
        }

        class Builder(
            private var context: Context
        ) {

            init {
                instance = FTAuth()
            }

            @Throws(IllegalStateException::class)
            fun build() {
                if (instance!!.serverUrl.isNullOrEmpty()) throw IllegalStateException(
                    "FTAuth server url is null or empty."
                )
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

    private fun checkValidAllElements(): Boolean {
        var isValid = true
        getInstance().requiredElements.forEach {
            when (it) {
                TypeElement.NONE -> return@forEach
                TypeElement.FIRST_NAME -> isValid =
                    if (isValid) registerFirstNameInputField?.isFirstNameValid ?: false else isValid
                TypeElement.LAST_NAME -> isValid =
                    if (isValid) registerLastNameInputField?.isLastNameValid ?: false else isValid
                TypeElement.PASSWORD -> isValid =
                    if (isValid) registerPasswordInputField?.isPasswordValid ?: false else isValid
                TypeElement.CONFIRM_PASSWORD -> isValid =
                    if (isValid) registerConfirmPasswordInputField?.isPasswordValid
                        ?: false else isValid
                TypeElement.EMAIL -> isValid =
                    if (isValid) registerEmailInputField?.isEmailValid
                        ?: false else isValid
            }
        }
        return isValid
    }

    @Throws(IllegalStateException::class)
    fun login() {
        if (authEmailInputField == null || authPasswordInputField == null)
            throw IllegalStateException(
                "FTAuth email input field and password input field can't be null"
            )

        val uc = LoginUserUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable(authEmailInputField!!.value, authPasswordInputField!!.value)
                .flatMap {
                    authRepository!!.getToken()
                }
                .async()
                .subscribe({
                    onLoginSuccess?.invoke()
                }, {
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
                        onRegistrationFailure?.invoke(it)
                    })
            )
        }

    }

    fun logOut() {
        val uc = LogOutUserUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable()
                .async()
                .subscribe({
                    onLogOutSuccess?.invoke()
                }, {
                    onLogOutFailure?.invoke(it)
                })
        )
    }

    fun getAuthToken() = instance!!.authRepository!!.getAuthToken()

    fun onStop() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }
}

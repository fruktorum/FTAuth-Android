package com.fruktorum.ftauth

import android.content.Context
import com.fruktorum.ftauth.customUI.auth.FTEmailInputField
import com.fruktorum.ftauth.customUI.auth.FTPasswordInputField
import com.fruktorum.ftauth.network.AuthLocalDataProvider
import com.fruktorum.ftauth.network.RetrofitHelper
import com.fruktorum.ftauth.network.repository.AuthRepository
import com.fruktorum.ftauth.network.usecase.LoginUserUseCase
import com.fruktorum.ftauth.util.constants.PrefsConstants
import com.fruktorum.ftauth.util.extensions.async
import io.reactivex.disposables.CompositeDisposable

class FTAuth {

    private var authRepository: AuthRepository? = null

    var onLoginSuccess: (() -> Unit?)? = null
    var onLoginFailure: ((Throwable) -> Unit?)? = null

    var onRegistrationSuccess: (() -> Unit?)? = null

    //Custom UI fields


    var serverUrl: String? = null

    var disposables = CompositeDisposable()

    companion object {
        private var instance: FTAuth? = null
        var authEmailInputField: FTEmailInputField? = null
        var authPasswordInputField: FTPasswordInputField? = null


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

    @Throws(IllegalStateException::class)
    fun register() {

    }

    fun onStop() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }
}

package com.fruktorum.ftauth

import android.content.Context
import com.fruktorum.ftauth.custom.FTEmailInputField
import com.fruktorum.ftauth.custom.FTPasswordInputField
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
    var serverUrl: String? = null

    var disposables = CompositeDisposable()

    companion object {
        private var instance: FTAuth? = null

        @Synchronized
        @Throws(IllegalStateException::class)
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


    fun login(emailField: FTEmailInputField, passwordField: FTPasswordInputField) {
        if (!emailField.isEmailValid || !passwordField.isPasswordValid) return
        val uc = LoginUserUseCase(instance!!.authRepository!!)
        disposables.add(
            uc.createObservable(emailField.value, passwordField.value)
                .flatMap {
                    authRepository!!.getToken()
                }
                .async()
                .subscribe({
                    onLoginSuccess?.invoke()
                }, {})
        )

    }

    fun onStop() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }
}

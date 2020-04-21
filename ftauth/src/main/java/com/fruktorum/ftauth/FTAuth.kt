package com.fruktorum.ftauth

import android.content.Context
import com.fruktorum.ftauth.custom.FTEmailInputField
import com.fruktorum.ftauth.custom.FTPasswordInputField

class FTAuth {

    var onLoginSuccess: (() -> Unit?)? = null

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

        public class Builder(
            private var context: Context
        ) {
            private var serverUrl: String? = null

            init {
                instance = FTAuth()
            }

            fun build() {


            }

            fun setServerUrl(serverUrl: String?): Builder {
                this.serverUrl = serverUrl
                return this
            }
        }

    }


    fun login(emailField: FTEmailInputField, passwordField: FTPasswordInputField) {

    }
}

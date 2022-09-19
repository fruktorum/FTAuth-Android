package com.fruktorum.ftauth.data

import android.util.Log
import android.widget.Toast
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.data.base.ErrorModelDeserializer
import com.fruktorum.ftauth.data.base.ErrorResponseModel
import com.fruktorum.ftauth.data.base.MethodType
import com.fruktorum.ftauth.data.error.Error
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

class ErrorHandler {

    fun handle(throwable: Throwable, methodType: MethodType) {
        if (throwable is HttpException) {
            try {
                val errorModel = gson.fromJson(
                    throwable.response()!!.errorBody()!!.string(),
                    ErrorResponseModel::class.java
                )
                if (errorModel.isErrorExisted()) {
                    errorModel.error?.let { error ->
                        when (Error.getErrorType(error)) {
                            Error.EMAIL_EXISTS -> {
                                FTAuth.registerEmailInputField?.setErrorMessage(ERROR_MSG_EMAIL_EXISTED)
                            }
                            Error.EMAIL_INVALID -> {
                                val fieldToShowError = if(methodType == MethodType.AUTH) FTAuth.authEmailInputField
                                else FTAuth.authEmailInputField
                                fieldToShowError?.setErrorMessage(ERROR_MSG_EMAIL_IS_INVALID)
                            }
                            Error.PASSWORD_INVALID -> {
                                val fieldToShowError = if(methodType == MethodType.AUTH) FTAuth.authPasswordInputField
                                else FTAuth.registerPasswordInputField
                                fieldToShowError?.setErrorMessage(ERROR_MSG_PASSWORD_IS_INVALID)
                            }
                            Error.PASSWORD_TOO_SHORT -> {
                                val fieldToShowError = if(methodType == MethodType.AUTH) FTAuth.authPasswordInputField
                                else FTAuth.registerPasswordInputField
                                fieldToShowError?.setErrorMessage(ERROR_MSG_PASSWORD_TOO_SHORT)
                            }
                            //TODO Добавить виды ошибок для Google/Facebook/Logout
                            else -> {
                                handleUnknownTypeError(error.message)
                            }
                        }
                    }
                } else {
                    Log.d(FTAuth.TAG, "${methodType.name} error. Messages are null")
                }
            } catch (ex: JsonSyntaxException) {
                Log.d(FTAuth.TAG, ex.message.toString())
            }
        }
    }

    private fun handleUnknownTypeError(error: String?) {
        // TODO продумать, что делаем с ошибкой, допустим ошибка на сервере
        // TODO Log.d(FTAuth.TAG, "Thrown error by: $error")
    }

    private val gson =
        GsonBuilder()
            .registerTypeAdapter(
                ErrorResponseModel::class.java,
                ErrorModelDeserializer()
            )
            .create()

    companion object {
        const val ERROR_MSG_EMAIL_EXISTED = "Email existed"
        const val ERROR_MSG_EMAIL_IS_INVALID = "Email is invalid"
        const val ERROR_MSG_PASSWORD_IS_INVALID = "Password is invalid"
        const val ERROR_MSG_PASSWORD_TOO_SHORT = "Password too short"
    }
}
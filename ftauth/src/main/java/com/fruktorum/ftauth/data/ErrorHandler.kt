package com.fruktorum.ftauth.data

import android.util.Log
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.data.base.ErrorModelDeserializer
import com.fruktorum.ftauth.data.base.ErrorResponseModel
import com.fruktorum.ftauth.data.base.MethodType
import com.fruktorum.ftauth.data.error.Error
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

class ErrorHandler {

    private val gson =
        GsonBuilder()
            .registerTypeAdapter(
                ErrorResponseModel::class.java,
                ErrorModelDeserializer()
            )
            .create()


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
                                FTAuth.registerEmailInputField?.setErrorMessage("Email existed")
                            }
                            Error.EMAIL_INVALID -> {
                                val fieldToShowError = if(methodType == MethodType.AUTH) FTAuth.authEmailInputField
                                else FTAuth.authEmailInputField
                                fieldToShowError?.setErrorMessage("Email is invalid")
                            }
                            Error.PASSWORD_INVALID -> {
                                val fieldToShowError = if(methodType == MethodType.AUTH) FTAuth.authPasswordInputField
                                else FTAuth.registerPasswordInputField
                                fieldToShowError?.setErrorMessage("Password is invalid")
                            }
                            Error.PASSWORD_TOO_SHORT -> {
                                val fieldToShowError = if(methodType == MethodType.AUTH) FTAuth.authPasswordInputField
                                else FTAuth.registerPasswordInputField
                                fieldToShowError?.setErrorMessage("Password too short")
                            }
                            else -> {
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


}
package com.fruktorum.ftauth.data

import android.util.Log
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.data.base.ErrorModelDeserializer
import com.fruktorum.ftauth.data.base.ErrorResponseModel
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


    fun handle(throwable: Throwable) {
        if (throwable is HttpException) {
            try {
                val errorModel = gson.fromJson(
                    throwable.response().errorBody()!!.string(),
                    ErrorResponseModel::class.java
                )
                if (errorModel.isErrorExisted()) {
                    errorModel.error?.let { error ->
                        when (Error.getErrorType(error)) {
                            Error.EMAIL_EXISTS -> {
                                Log.d("FTAuth", "email exist error")
                                FTAuth.registerEmailInputField?.setErrorMessage("Email existed")
                            }
                            Error.PASSWORD_INVALID -> {

                            }
                            else -> {
                            }
                        }
                    }
                } else {
                    Log.d("FTAuth", "Messages are null")
                }
            } catch (ex: JsonSyntaxException) {
                Log.d("FTAuth", ex.message.toString())
            }
        }
    }


}
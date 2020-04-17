package com.fruktorum.ftauth.data.auth.responseModel


import com.google.gson.annotations.SerializedName

data class RegisterUserResponseModel(
    @SerializedName("provider_token")
    val providerToken: String,
    @SerializedName("session_token")
    val sessionToken: String,
    @SerializedName("success")
    val success: Boolean
)
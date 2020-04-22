package com.fruktorum.ftauth.data.auth.dataModel


import com.google.gson.annotations.SerializedName

internal data class LoginUserDataModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
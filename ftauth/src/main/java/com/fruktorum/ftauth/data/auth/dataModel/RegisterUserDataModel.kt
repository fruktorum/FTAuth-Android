package com.fruktorum.ftauth.data.auth.dataModel


import com.google.gson.annotations.SerializedName

data class RegisterUserDataModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String
    )
}
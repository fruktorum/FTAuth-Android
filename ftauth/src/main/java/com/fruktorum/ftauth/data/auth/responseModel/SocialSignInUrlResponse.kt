package com.fruktorum.ftauth.data.auth.responseModel


import com.google.gson.annotations.SerializedName

internal data class SocialSignInUrlResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("url")
    val url: String
)
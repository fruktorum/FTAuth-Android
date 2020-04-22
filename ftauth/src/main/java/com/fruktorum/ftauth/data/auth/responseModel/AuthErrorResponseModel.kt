package com.fruktorum.ftauth.data.auth.responseModel

import com.google.gson.annotations.SerializedName

internal class AuthErrorResponseModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("errors") val errors: List<Errors>
) {

    data class Errors(
        @SerializedName("key") val key: String,
        @SerializedName("message") val message: String
    )
}

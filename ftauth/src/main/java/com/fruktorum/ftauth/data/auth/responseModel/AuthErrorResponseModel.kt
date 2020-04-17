package com.fruktorum.cbdmobile.data.auth.responseModel

import com.google.gson.annotations.SerializedName

class AuthErrorResponseModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("errors") val errors: List<Errors>
) {

    data class Errors(
        @SerializedName("key") val key: String,
        @SerializedName("message") val message: String
    )
}

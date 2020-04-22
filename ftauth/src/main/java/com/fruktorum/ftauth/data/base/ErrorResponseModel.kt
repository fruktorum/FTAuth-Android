package com.fruktorum.ftauth.data.base


import com.google.gson.annotations.SerializedName

internal data class ErrorResponseModel(
    @SerializedName("errors")
    val errors: List<Errors>,
    @SerializedName("success")
    val success: Boolean
) {
    data class Errors(
        @SerializedName("key")
        val key: String,
        @SerializedName("messages")
        val messages: List<String>
    )
}




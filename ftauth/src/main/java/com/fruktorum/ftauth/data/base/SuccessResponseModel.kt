package com.fruktorum.ftauth.data.base


import com.google.gson.annotations.SerializedName

internal data class SuccessResponseModel(
    @SerializedName("success")
    val success: Boolean
)
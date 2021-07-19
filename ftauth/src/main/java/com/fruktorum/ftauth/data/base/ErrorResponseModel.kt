package com.fruktorum.ftauth.data.base


import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


internal data class ErrorResponseModel(
    var error: Errors? = null,
    @SerializedName("success")
    val success: Boolean
) {
    fun isErrorExisted() = error != null && (error?.message != null
            || error?.messages != null)


    data class Errors(
        @SerializedName("key")
        val key: String,
        @SerializedName("messages")
        val messages: List<String>? = null,
        @SerializedName("message")
        val message: String?
    )
}

internal class ErrorModelDeserializer : JsonDeserializer<ErrorResponseModel?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ErrorResponseModel {
        val errorModel: ErrorResponseModel = Gson().fromJson(json, ErrorResponseModel::class.java)
        val jsonObject = json?.asJsonObject
        jsonObject?.let { jsonModel ->
            if (jsonModel.has("errors")) {
                val elem = jsonModel.get("errors")
                if (elem != null && !elem.isJsonNull) {
                    try {
                        if (elem.isJsonArray) {
                            val errors: List<ErrorResponseModel.Errors> = Gson().fromJson(
                                elem,
                                object : TypeToken<List<ErrorResponseModel.Errors?>?>() {}.type
                            )
                            errorModel.error = errors.firstOrNull()
                        } else {
                            val errorString = elem.asString
                            errorModel.error = ErrorResponseModel.Errors("", message = errorString)
                        }
                    } catch (ex: Exception) {

                    }
                }
            }
        }
        return errorModel
    }
}




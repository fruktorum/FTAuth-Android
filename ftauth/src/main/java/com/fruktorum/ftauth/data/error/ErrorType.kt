package com.fruktorum.ftauth.data.error

import com.fruktorum.ftauth.data.base.ErrorResponseModel

internal enum class Error(val key: String?, val message: String) {
    EMAIL_INVALID("email", "invalid"),
    EMAIL_EXISTS("email", "exists"),
    PASSWORD_INVALID("password", "invalid"),
    PASSWORD_TOO_SHORT("password", "too_short"),
    UNKNOWN("", "unknown");

    companion object {
        fun getErrorType(errors: ErrorResponseModel.Errors): Error {
            val message = errors.message ?: (errors.messages?.firstOrNull() ?: "")
            return values().firstOrNull { it.message == message && it.key ?: "" == errors.key }
                ?: UNKNOWN
        }

        fun getErrorType(errorMessage: String): Error {
            return values().firstOrNull { it.message == errorMessage }
                ?: UNKNOWN
        }
    }

}
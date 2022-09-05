package com.fruktorum.ftauth.network

import android.content.SharedPreferences
import com.fruktorum.ftauth.util.constants.PrefsConstants
import com.fruktorum.ftauth.util.extensions.get
import com.fruktorum.ftauth.util.extensions.set
import io.reactivex.Observable

internal class AuthLocalDataProvider
constructor(private val prefs: SharedPreferences) {

    fun saveTokens(sessionToken: String, providerToken: String): Observable<Boolean> {
        return Observable.fromCallable {
            prefs[PrefsConstants.SESSION_TOKEN] = sessionToken
            prefs[PrefsConstants.PROVIDER_TOKEN] = providerToken
            true
        }
    }

    fun getSessionToken(): String {
        return prefs[PrefsConstants.SESSION_TOKEN, ""]!!
    }

    fun getProviderToken(): String {
        return prefs[PrefsConstants.PROVIDER_TOKEN, ""]!!
    }

    fun clearTokens(): Observable<Boolean> {
        return Observable.fromCallable {
            prefs[PrefsConstants.SESSION_TOKEN] = ""
            prefs[PrefsConstants.PROVIDER_TOKEN] = ""
            true
        }
    }

    fun setSessionToken(sessionToken: String) {
        prefs[PrefsConstants.SESSION_TOKEN] = sessionToken
    }

    fun setProviderToken(providerToken: String) {
        prefs[PrefsConstants.PROVIDER_TOKEN] = providerToken
    }


}
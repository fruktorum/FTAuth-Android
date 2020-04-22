package com.fruktorum.ftauth.network

import android.content.SharedPreferences
import com.fruktorum.ftauth.util.constants.PrefsConstants
import com.fruktorum.ftauth.util.extensions.get
import com.fruktorum.ftauth.util.extensions.set
import io.reactivex.Observable

class AuthLocalDataProvider
constructor(private val prefs: SharedPreferences) {

    fun saveToken(token: String): Observable<Boolean> {
        return Observable.fromCallable {
            prefs[PrefsConstants.SESSION_TOKEN] = token
            true
        }
    }

    fun getToken(): Observable<String> {
        return Observable.fromCallable {
            prefs[PrefsConstants.SESSION_TOKEN, ""]
        }
    }

    fun clearToken(): Observable<Boolean> {
        return Observable.fromCallable {
            prefs[PrefsConstants.SESSION_TOKEN] = ""
            true
        }
    }


}
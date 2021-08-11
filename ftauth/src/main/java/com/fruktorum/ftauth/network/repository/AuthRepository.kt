package com.fruktorum.ftauth.network.repository

import com.fruktorum.ftauth.data.auth.dataModel.LoginUserDataModel
import com.fruktorum.ftauth.data.auth.dataModel.RegisterUserDataModel
import com.fruktorum.ftauth.data.auth.responseModel.LoginUserResponseModel
import com.fruktorum.ftauth.data.auth.responseModel.RegisterUserResponseModel
import com.fruktorum.ftauth.data.auth.responseModel.SocialSignInUrlResponse
import com.fruktorum.ftauth.data.base.SuccessResponseModel
import com.fruktorum.ftauth.network.AuthLocalDataProvider
import com.fruktorum.ftauth.network.provider.AuthGlobalDataProvider
import io.reactivex.Observable

internal class AuthRepository(
    private val authGlobalDataProvider: AuthGlobalDataProvider,
    private val localDataProvider: AuthLocalDataProvider
) {

    fun getGoogleSignInUrl(): Observable<SocialSignInUrlResponse> {
        return authGlobalDataProvider.getGoogleSignInUrl()
    }

    fun getFacebookSignInUrl(): Observable<SocialSignInUrlResponse> {
        return authGlobalDataProvider.getFacebookSignInUrl()
    }

    fun register(registerUserDataModel: RegisterUserDataModel): Observable<RegisterUserResponseModel> {
        return authGlobalDataProvider.register(registerUserDataModel)
    }

    fun login(loginUserDataModel: LoginUserDataModel): Observable<LoginUserResponseModel> {
        return authGlobalDataProvider.login(loginUserDataModel)
    }

    fun logOut(token: String): Observable<SuccessResponseModel> {
        return authGlobalDataProvider.logOut(token)
    }

    fun saveTokens(sessionToken: String, providerToken: String): Observable<Boolean> {
        return localDataProvider.saveTokens(sessionToken, providerToken)
    }


    fun getSessionTokenAsync(): Observable<String> {
        return Observable.fromCallable {
            localDataProvider.getSessionToken()
        }
    }

    fun getSessionToken(): String = localDataProvider.getSessionToken()

    fun getProviderToken(): String = localDataProvider.getProviderToken()

    fun setSessionToken(sessionToken: String) = localDataProvider.setSessionToken(sessionToken)

    fun setProviderToken(providerToken: String) = localDataProvider.setProviderToken(providerToken)

    fun clearToken(): Observable<Boolean> {
        return localDataProvider.clearTokens()
    }


}
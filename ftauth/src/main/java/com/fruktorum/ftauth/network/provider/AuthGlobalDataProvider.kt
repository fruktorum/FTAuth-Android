package com.fruktorum.ftauth.network.provider


import com.fruktorum.ftauth.data.auth.dataModel.LoginUserDataModel
import com.fruktorum.ftauth.data.auth.dataModel.RegisterUserDataModel
import com.fruktorum.ftauth.data.auth.responseModel.LoginUserResponseModel
import com.fruktorum.ftauth.data.auth.responseModel.RegisterUserResponseModel
import com.fruktorum.ftauth.data.auth.responseModel.SocialSignInUrlResponse
import com.fruktorum.ftauth.data.base.SuccessResponseModel
import io.reactivex.Observable
import retrofit2.http.*

interface AuthGlobalDataProvider {

    @POST("/email/register")
    fun register(@Body registerUserDataModel: RegisterUserDataModel): Observable<RegisterUserResponseModel>

    @POST("/email/auth")
    fun login(@Body loginUserDataModel: LoginUserDataModel): Observable<LoginUserResponseModel>

    @POST("/session/logout")
    fun logOut(@Header("session_token") token: String): Observable<SuccessResponseModel>

    @GET("/google/link")
    fun getGoogleSignInUrl(@Query("mobile") isMobile: Boolean = true): Observable<SocialSignInUrlResponse>

    @GET("/facebook/link")
    fun getFacebookSignInUrl(@Query("mobile") isMobile: Boolean = true): Observable<SocialSignInUrlResponse>

}
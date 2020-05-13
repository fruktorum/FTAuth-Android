package com.fruktorum.ftauth.network.usecase

import com.fruktorum.ftauth.data.auth.responseModel.SocialSignInUrlResponse
import com.fruktorum.ftauth.network.repository.AuthRepository
import io.reactivex.Observable

internal class GetFacebookSignInUrlUseCase
constructor(private val repository: AuthRepository) {

    fun createObservable(): Observable<SocialSignInUrlResponse> {
        return repository.getFacebookSignInUrl()

    }
}
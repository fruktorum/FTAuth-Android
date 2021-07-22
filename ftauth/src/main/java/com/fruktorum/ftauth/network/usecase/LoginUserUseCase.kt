package com.fruktorum.ftauth.network.usecase


import com.fruktorum.ftauth.data.auth.dataModel.LoginUserDataModel
import com.fruktorum.ftauth.network.repository.AuthRepository
import io.reactivex.Observable

internal class LoginUserUseCase
constructor(private val repository: AuthRepository) {

    fun createObservable(email: String, password: String): Observable<Boolean> {
        return repository.login(LoginUserDataModel(email, password))
            .flatMap {
                repository.saveTokens(it.sessionToken, it.providerToken)
            }
    }
}
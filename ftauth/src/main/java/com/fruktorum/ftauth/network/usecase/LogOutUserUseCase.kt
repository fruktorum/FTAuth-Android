package com.fruktorum.ftauth.network.usecase

import com.fruktorum.ftauth.network.repository.AuthRepository
import io.reactivex.Observable

internal class LogOutUserUseCase
constructor(
    private val repository: AuthRepository
) {

    fun createObservable(): Observable<Boolean> {
        return repository.getToken()
            .flatMap {
                repository.logOut(it)
            }
            .flatMap {
                repository.clearToken()
            }


    }
}
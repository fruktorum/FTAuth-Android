package com.fruktorum.ftauth.network.usecase

import com.fruktorum.ftauth.data.auth.dataModel.RegisterUserDataModel
import com.fruktorum.ftauth.network.repository.AuthRepository
import io.reactivex.Observable

internal class RegisterUserUseCase
constructor(private val repository: AuthRepository) {

    fun createObservable(dataModel: RegisterUserDataModel?): Observable<Boolean> {
        return repository.register(dataModel!!)
            .flatMap {
                repository.saveToken(it.sessionToken)
            }

    }
}
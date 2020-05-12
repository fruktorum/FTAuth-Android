package com.fruktorum.ftauthsample.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.tracked() {
        compositeDisposable.add(this)
    }

    private fun unsubscribeAll() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.isDisposed
            compositeDisposable.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeAll()
    }


}
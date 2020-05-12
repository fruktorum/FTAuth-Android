package com.fruktorum.ftauthsample.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : MvpAppCompatFragment() {

    private val compositeDisposable = CompositeDisposable()

    abstract val layoutRes: Int

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderView(view, savedInstanceState)
    }


    abstract fun renderView(view: View, savedInstanceState: Bundle?)


    fun Disposable.tracked() {
        compositeDisposable.add(this)
    }

    private fun unsubscribeAll() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unsubscribeAll()
    }
}
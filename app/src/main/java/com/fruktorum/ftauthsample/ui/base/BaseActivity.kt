package com.fruktorum.ftauthsample.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector {

    private val compositeDisposable = CompositeDisposable()

    abstract val layoutRes: Int

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return androidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        renderView(savedInstanceState)
    }

    protected abstract fun renderView(savedInstanceState: Bundle?)


    fun Disposable.tracked() {
        compositeDisposable.add(this)
    }

    private fun unsubscribeAll() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeAll()
    }
}
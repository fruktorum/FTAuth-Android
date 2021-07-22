package com.fruktorum.ftauth.customUI.webView

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.util.constants.PrefsConstants
import com.fruktorum.ftauth.util.extensions.set
import io.reactivex.disposables.CompositeDisposable

internal class WebViewActivity : AppCompatActivity(R.layout.activity_webview) {

    private val url: String by lazy {
        intent.extras?.getString(WEB_VIEW_URL) ?: ""
    }

    private val webView: WebView by lazy {
        findViewById(R.id.web_view)
    }

    private val compositeDisposable = CompositeDisposable()
    private val prefs: SharedPreferences by lazy {
        this.getSharedPreferences(
            this.packageName + PrefsConstants.APP_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        if (url.contains("google")) webView.settings.userAgentString =
            System.getProperty("http.agent")

        webView.settings.javaScriptEnabled = true

        webView.clearCache(true)

        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return handleUri(request?.url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                handleError(request?.url)
            }
        }
    }

    private fun handleError(url: Uri?) {
        url?.let {
            val errorReason = url.getQueryParameter("error_reason")
            if (errorReason != null && errorReason == "user_denied") {
                Log.d("WebViewError", errorReason)
                finish()
            }
        }

    }

    private fun handleUri(url: Uri?): Boolean {
        val sessionToken = url!!.getQueryParameter("session_token")
        return if (sessionToken != null) {
            prefs[PrefsConstants.SESSION_TOKEN] = sessionToken
            finish()
            FTAuth.getInstance().onLoginSuccess?.invoke()
            true
        } else false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    companion object {
        const val WEB_VIEW_URL = "webview_url"
    }
}
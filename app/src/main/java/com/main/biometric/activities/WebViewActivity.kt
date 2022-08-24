package com.main.biometric.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.main.biometric.FreshworksApplication
import com.main.biometric.databinding.ActivityWebViewBinding

const val webUrl: String = "https://www.freshworks.com/"

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FreshworksApplication[this].getAppComponent().inject(this)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.errorView.errorTryAgain.setOnClickListener {
            webViewSetup()
        }
        webViewSetup()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            loadUrl(webUrl).also {
                binding.apply {
                    progressBar.visibility = View.VISIBLE
                    errorView.errorViewLayout.isVisible = false
                }
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    binding.errorView.errorViewLayout.isVisible = true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
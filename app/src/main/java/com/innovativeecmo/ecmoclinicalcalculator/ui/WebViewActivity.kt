package com.innovativeecmo.ecmoclinicalcalculator.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.innovativeecmo.ecmoclinicalcalculator.R
import com.innovativeecmo.ecmoclinicalcalculator.databinding.ActivityWebViewBinding
import com.innovativeecmo.ecmoclinicalcalculator.utils.Constants.Companion.KEY_WEB_PAGE

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var url = intent.getStringExtra(KEY_WEB_PAGE)

        binding.progressHorizontal.visibility = View.VISIBLE
        url?.let { binding.webviewTerms.loadUrl(it) }
        binding.webviewTerms.settings.javaScriptEnabled = true
        binding.webviewTerms.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(binding.webviewTerms, url)
                binding.progressHorizontal.visibility = View.GONE
            }
        }
    }
}
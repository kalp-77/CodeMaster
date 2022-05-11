package com.example.mastercoderapp.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mastercoderapp.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        window.statusBarColor = Color.BLACK
        window.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        val url = intent.getStringExtra("URL")

        if(url!=null){
            webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webView.settings.setAppCacheEnabled(true)
            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webView.settings.domStorageEnabled = true
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            webView.settings.javaScriptEnabled = true
            webView.settings.userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"

            webView.setInitialScale(1);
            webView.settings.loadWithOverviewMode = true;
            webView.settings.useWideViewPort = true;
            webView.settings.setSupportZoom(true);
            webView.settings.builtInZoomControls = true; // allow pinch to zooom
            webView.settings.displayZoomControls =
                false; // disable the default zoom controls on the page

            webView.webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    ProgressBar.visibility = View.GONE
                    webView.visibility = View.VISIBLE
                }
            }
            webView.loadUrl(url)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}



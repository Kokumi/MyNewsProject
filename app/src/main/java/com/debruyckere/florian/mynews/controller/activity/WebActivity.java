package com.debruyckere.florian.mynews.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.debruyckere.florian.mynews.R;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView mWebView = findViewById(R.id.Web_View);
        mWebView.setWebViewClient(new WebViewClient());

        // load the URL in intent to load the news
        String newURL = getIntent().getStringExtra("URL");
        Log.d("WEBVIEW",newURL);
        mWebView.loadUrl(newURL);
    }
}

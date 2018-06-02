package com.debruyckere.florian.mynews.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.debruyckere.florian.mynews.R;

import butterknife.BindView;

public class WebActivity extends AppCompatActivity {

    //@BindView(R.id.Web_View) WebView mWebView;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = findViewById(R.id.Web_View);
        mWebView.setWebViewClient(new WebViewClient());
        String newURL = getIntent().getStringExtra("URL");
        Log.d("WEBVIEW",newURL);
        mWebView.loadUrl(newURL);
    }
}

package com.example.idrivetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {
      WebView webview;

  @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_webview);
      webview=findViewById(R.id.web);

      Bundle intent=getIntent().getExtras();
      String url = intent.getString("url");
      webview.loadUrl(url);

    }
}

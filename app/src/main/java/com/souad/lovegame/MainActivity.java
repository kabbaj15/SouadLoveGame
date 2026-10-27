package com.souad.lovegame;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.ValueCallback;
import android.net.Uri;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.graphics.Color;
import java.io.InputStream;

public class MainActivity extends Activity {
  private WebView web;
  private ValueCallback<Uri[]> fileCallback;
  private static final int PICK_IMAGE = 42;
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    web = new WebView(this);
    web.setBackgroundColor(Color.rgb(255,245,250));
    setContentView(web);
    WebSettings s = web.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setAllowFileAccess(false);
    s.setAllowContentAccess(true);
    web.setWebChromeClient(new WebChromeClient() {
      @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
        if (fileCallback != null) fileCallback.onReceiveValue(null);
        fileCallback = callback;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
          startActivityForResult(Intent.createChooser(intent, "اختار صورة"), PICK_IMAGE);
          return true;
        } catch (android.content.ActivityNotFoundException e) {
          fileCallback = null;
          callback.onReceiveValue(null);
          return false;
        }
      }
    });
    web.setWebViewClient(new WebViewClient() {
      @Override public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        String prefix = "https://appassets.androidplatform.net/assets/";
        if (!url.startsWith(prefix)) return super.shouldInterceptRequest(view, request);
        String filename = url.substring(prefix.length());
        if (!(filename.equals("index.html") || filename.equals("app.js") || filename.equals("style.css") || filename.equals("config.js"))) return null;
        try {
          InputStream input = getAssets().open(filename);
          String mime = filename.endsWith(".html") ? "text/html" : filename.endsWith(".css") ? "text/css" : "application/javascript";
          return new WebResourceResponse(mime, "UTF-8", input);
        } catch (Exception e) { return null; }
      }
    });
    web.loadUrl("https://appassets.androidplatform.net/assets/index.html");
  }
  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE && fileCallback != null) {
      Uri[] result = resultCode == RESULT_OK && data != null && data.getData() != null ? new Uri[]{data.getData()} : null;
      fileCallback.onReceiveValue(result); fileCallback = null;
    }
  }
  @Override public void onBackPressed() { if (web.canGoBack()) web.goBack(); else super.onBackPressed(); }
  @Override protected void onDestroy() { if (fileCallback != null) { fileCallback.onReceiveValue(null); fileCallback = null; } web.destroy(); super.onDestroy(); }
}

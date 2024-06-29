package com.dio.iphone.services;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.dio.iphone.interfaces.WebBrowser;

public class WebService implements WebBrowser {
    private Context context;
    private WebView webView;

    public WebService(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void exibirPagina(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void adicionarNovaAba() {
        WebView newWebView = new WebView(context);
        newWebView.setWebViewClient(new WebViewClient());
        newWebView.getSettings().setJavaScriptEnabled(true);
        newWebView.loadUrl("about:blank");
    }

    @Override
    public void atualizarPagina() {
        webView.reload();
    }
}
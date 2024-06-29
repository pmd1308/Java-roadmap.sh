package com.dio.iphone.models;

import android.content.Context;
import android.webkit.WebView;
import com.dio.iphone.servicer.MusicService;
import com.dio.iphone.services.PhoneService;
import com.dio.iphone.services.WebService;

public class iPhone {
    private MusicService musicService;
    private PhoneService phoneService;
    private WebService webService;

    public iPhone(Context context, WebView webView) {
        this.musicService = new MusicService(context);
        this.phoneService = new PhoneService(context);
        this.webService = new WebService(context, webView);
    }

    public void usarReprodutorMusical() {
        musicService.tocar();
    }

    public void usarAparelhoTelefonico() {
        phoneService.ligar("123456789");
    }

    public void atenderChamada() {
        phoneService.atender();
    }

    public void usarNavegadorInternet() {
        webService.exibirPagina("https://www.google.com");
    }

    public void adicionarAba() {
        webService.adicionarNovaAba();
    }

    public void atualizarPagina() {
        webService.atualizarPagina();
    }
}
package com.dio.iphone;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.dio.iphone.*;

public class MainActivity extends AppCompatActivity {
    private Iphone mueIphone;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        meuIphone = new iPhone(this, webView); 

        meuIphone.usarReprodutorMusical();
        meuIphone.usarAparelhoTelefonico();
        meuIphone.usarNavegadorInternet();
    }
}
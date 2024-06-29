package com.dio.iphone.services;

import android.content.Context;
import android.telecom.TelecomManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest;
import android.app.Activity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.dio.iphone.interfaces.Phone;

public class PhoneService implements Phone {
    private Context context;

    public PhoneService(Context context) {
        this.context = context;
    }

    @Override
    public void ligar(String numero) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + numero));
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public void atender() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ANSWER_PHONE_CALLS}, 1);
            } else {
                telecomManager.acceptRingingCall();
            }
        } else {
            // Em versões anteriores, o método não está disponível
        }
    }

    @Override
    public void iniciarCorreioVoz() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:123"));
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            context.startActivity(intent);
        }
    }
}

package com.dio.iphone.services;

import android.content.Context;
import android.media.MediaPlayer;
import com.dio.iphone.interfaces.MusicPlayer;
import com.dio.iphone.utils.DataSanitizer;

public class MusicService implements MusicPlayer {
    private MediaPlayer mediaPlayer;
    private Context context;

    public MusicService(Context context) {
        this.context = context;
    }

    @Override
    public void tocar() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.sample_music); 
            mediaPlayer.setOnCompletionListener(mp -> pausar());
        }
        mediaPlayer.start();
    }

    @Override
    public void pausar() {
        if (mediaPlayer!= null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void selecionarMusica(String musica) {
        String sanitizedMusic = DataSanitizer.sanitize(musica);
        int resID = context.getResources().getIdentifier(sanitizedMusic, "raw", context.getPackageName());
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, resID);
        mediaPlayer.setOnCompletionListener(mp -> pausar());
    }
}
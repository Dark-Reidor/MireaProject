package ru.mirea.linnik.mireaproject.ui.audio;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import ru.mirea.linnik.mireaproject.R;

public class AudioService extends Service {
    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        mediaPlayer=MediaPlayer.create(this, R.raw.bictor_coy_grupa_crovi);
        mediaPlayer.setLooping(true);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}
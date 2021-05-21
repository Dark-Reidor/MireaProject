package ru.mirea.linnik.mireaproject.ui.permission;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

import static ru.mirea.linnik.mireaproject.ui.permission.PermissionFragment.fileName;

public class RecorderService extends Service {
    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        try {
            mediaPlayer = new MediaPlayer();

        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
        } catch (IOException e){
            e.printStackTrace();
        }

        mediaPlayer.start();
        return START_STICKY;
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}
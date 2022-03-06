package com.example.itsdonespeedquiz.Appservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.itsdonespeedquiz.R;

public class MusicService extends Service {
    private static final String TAG ="BackgrounMusicService";

    MediaPlayer player;

    public IBinder onBind(Intent intent){
        return null;
    }
    public void onCreate(){
        Log.d(TAG,"onCreate()");
        player = MediaPlayer.create(this, R.raw.bgm);
        player.setLooping(false);
    }
    public void onDestroy() {
        player.stop();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }
}

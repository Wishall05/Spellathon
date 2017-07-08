package com.wishall.spellathon;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class Music{
    private MediaPlayer mp, mp1;
    private Context context;
    private static final String TAG = "Music";

    Music(Context context) {
        this.context = context;
    }

    public void playLongSound(int resoId) {
        if(mp!=null)
        {
            mp.release();
            mp=null;
        }
        mp = MediaPlayer.create(context, resoId);
        if(mp != null) {
            mp.start();
        } else {
            Log.e(TAG, "MediaPlayer doesn't exist");
        }
    }

    public void playSound(int resoId) {
        if(mp1!=null)
        {
            mp1.release();
            mp1=null;
        }
        mp1 = MediaPlayer.create(context, resoId);
        mp1.start();
    }
}

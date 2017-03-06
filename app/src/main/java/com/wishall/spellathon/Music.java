package com.wishall.spellathon;

import android.content.Context;
import android.media.MediaPlayer;

public class Music{
    MediaPlayer mp, mp1;
    Context context;

    Music(Context context) {
        this.context = context;
    }

    public void play(int resoId) {
        if(mp!=null)
        {
            mp.release();
            mp=null;
        }
        mp = MediaPlayer.create(context, resoId);
        mp.start();
    }

    public void playSound(int resoId) {
        mp1 = MediaPlayer.create(context, resoId);
        mp1.start();
    }
}

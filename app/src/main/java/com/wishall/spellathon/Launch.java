package com.wishall.spellathon;

/**
 * Created by Harshita on 03-07-2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Launch extends Activity implements OnClickListener {

    Button bPlay, bTimePlay;
    boolean timeMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.launch);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub

        BackSurfaceView sfvTrack = (BackSurfaceView) findViewById(R.id.surface_back);
      //  sfvTrack.setZOrderOnTop(true);    // necessary
        SurfaceHolder sfhTrack = sfvTrack.getHolder();
        sfhTrack.setFormat(PixelFormat.TRANSLUCENT);

        bPlay = (Button)findViewById(R.id.button_normal_play);
        bTimePlay = (Button)findViewById(R.id.button_time_play);
        bPlay.setOnClickListener(this);
        bTimePlay.setOnClickListener(this);
        timeMode = false;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
            case R.id.button_normal_play:
                timeMode = false;
                Intent startNormalGame = new Intent(Launch.this, Game.class);
                startNormalGame.putExtra("Mode", timeMode);
                Launch.this.startActivity(startNormalGame);
                overridePendingTransition(0, 0);
                break;
            case R.id.button_time_play:
                timeMode = true;
                Intent startTimedGame = new Intent(Launch.this, Game.class);
                startTimedGame.putExtra("Mode", timeMode);
                Launch.this.startActivity(startTimedGame);
                overridePendingTransition(0, 0);
                break;
        }

    }

}

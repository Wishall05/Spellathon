package com.wishall.spellathon;

/**
 * Created by Vishal on 03-07-2016.
 */
import android.content.Context;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Header extends RelativeLayout{

    private TextView tvScore, tvTimer;
    private int score;
    private final String scoreText = "SCORE - ";
    private final String timerText = "TIME - ";
    private int timerVal = 1000*10;//1000*60*3;
    private Game game;

    private static CountDownTimer gameTimer;
    private long millisecLeft;



    public Header(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init();
        //startTimer();
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
        //startTimer();
    }

    public Header(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
        //startTimer();
    }

    public int getTimerVal() {
        return timerVal;
    }

    public void setTimerVal(int timerVal) {
        this.timerVal = timerVal;
    }

	/*public void startTimer() {
		// TODO Auto-generated method stub
		final DateFormat df = new DateFormat();
		final Time t = new Time();
		gameTimer = new CountDownTimer(timerVal, 1000) {
			long min, sec;

		    public void onTick(long millisUntilFinished) {
		    	//t.set(millisUntilFinished/1000);
		       // tvTimer.setText(timerText + t.minute+":"+t.second);
		    	sec = millisUntilFinished/1000;
		    	min = sec/60;
		    	sec = sec - min*60;
		    	millisecLeft = millisUntilFinished;
		    	 tvTimer.setText(timerText + min +":"+ String.format("%02d", sec));
		       //here you can have your logic to set text to edittext
		    }

		    public void onFinish() {
		        tvTimer.setText("done!");
		        game.timerUp();
		    }

		}.start();
	}

	public void cancelTimer() {
		if(gameTimer != null) {
			gameTimer.cancel();
		}
	}

	public void resumeTimer() {
		//if(gameTimer == null) {
			timerVal = (int)millisecLeft;
			startTimer();
		//}
	}*/

    private void init() {
        // TODO Auto-generated method stub
        inflate(getContext(), R.layout.header, this);

        tvScore = (TextView) findViewById(R.id.textView_score);
        tvTimer = (TextView) findViewById(R.id.textView_timer);
        score = 0;
        setScore(0);
        millisecLeft = 1000*60*3;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        tvScore.setText(scoreText+score);
    }

    public void setTimerVal(long min, long sec) {
        if(min == -1 && sec == -1) {
            tvTimer.setText("Done!");
            return;
        }
        tvTimer.setText(timerText + min +":"+ String.format("%02d", sec));
    }

    public void setGame(Game game) {
        // TODO Auto-generated method stub
        this.game = game;
    }

}

package com.wishall.spellathon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.wishall.spellathon.com.wishall.spellathon.webview.WordClickListener;


public class Game extends Activity {


    private CustomHexagonsLayout hexsLayout;
    private WordArea wordArea;
    private Header header;
    private SpellAlgo spellAlgo;
    private char mainCh;
    private String restStr;
    private HashMap<String, Integer> hmAns;
    private GridView gvAns;
    private LinearLayout llAns;
    private ImageButton bShuffle, bHints;
    private Random randomno;
    boolean isGuessed[];
    TextView tvAns[];
    Music music;
    int tvAnsStatus[];
    boolean timeMode;
    private WordClickListener wordClickListener;

    private static CountDownTimer gameTimer;
    private long millisecLeft;
    private boolean isTimerRunning;


    private final static int LOWEST_WORD_SCORE = 10;

    private final static int STATUS_HIDE = 0;
    private final static int STATUS_HINT = 1;
    private final static int STATUS_SHOW = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        Intent launchIntent = getIntent();
        timeMode = launchIntent.getBooleanExtra("Mode", false);
        init();
        gameStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(timeMode && !isTimerRunning) {
            //header.setTimerVal(1000*10);
            startTimer();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(timeMode) {
            //header.setTimerVal(1000*10);
            //header.cancelTimer();
            gameTimer.cancel();
            isTimerRunning = false;
        }

    }

    private void init() {
        // TODO Auto-generated method stub
       // BackSurfaceView sfvTrack = (BackSurfaceView) findViewById(R.id.surface);
      //  sfvTrack.setZOrderOnTop(true);    // necessary
        //sfvTrack.send
      //  SurfaceHolder sfhTrack = sfvTrack.getHolder();
      //  sfhTrack.setFormat(PixelFormat.TRANSLUCENT);

        music = new Music(getApplicationContext());
        randomno = new Random();
        spellAlgo = new SpellAlgo(this);
        wordArea = (WordArea)findViewById(R.id.word_area);
        hexsLayout = (CustomHexagonsLayout)findViewById(R.id.custom_hex_layout);
        llAns = (LinearLayout)findViewById(R.id.ll_answers);
        llAns.setBackgroundColor(Color.parseColor("#77222222"));
        header = (Header)findViewById(R.id.header);
        hexsLayout.bringToFront();
        wordClickListener = new WordClickListener(getApplicationContext());

        bShuffle = (ImageButton)findViewById(R.id.ib_shuffle);
        bHints = (ImageButton)findViewById(R.id.ib_hint);
        bHints.setEnabled(true);
        bShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                restStr = spellAlgo.shuffle(restStr);
                shuffle(restStr);
                music.playSound(R.raw.shuffle);
            }
        });
        bHints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setTvAnsStatus(STATUS_HINT);
                music.playSound(R.raw.hint);
                bHints.setEnabled(false);
                setAnwsersTexts();
            }
        });
        //gvAns = (GridView)findViewById(R.id.gridview_answers);

        //Log.d("vishal",""+(findViewById(R.id.custom_hex_layout)==null)+""+(findViewById(R.id.word_area)==null));

        wordArea.setGame(this);
        hexsLayout.setGame(this);
        header.setGame(this);
        isTimerRunning = false;

    }

    private void gameStart() {
        // TODO Auto-generated method stub
		/*if(answerDialog != null) {
			answerDialog.dismiss();
		}*/
        play();
        // /
        llAns.removeAllViews();
        int totalColNo = 4;
        int ansCount = hmAns.size();
        int ROWS = ansCount / totalColNo + 1;
        tvAns = new TextView[ROWS*totalColNo];
        tvAnsStatus = new int[ansCount];
        LinearLayout llRowsAns[] = new LinearLayout[ROWS];
        int tvNo = 0, rowNo = 0, colNo = 0;

        setTvAnsStatus(STATUS_HIDE);
        Set<String> keySet = hmAns.keySet();
        Iterator<String> keySetIterator = keySet.iterator();
        int rowHeight = (int) convertDpToPixel(20, this.getApplicationContext());
        int textSize = (int) convertDpToPixel(20, this.getApplicationContext());


        while (rowNo < ansCount / totalColNo + 1) {
            llRowsAns[rowNo] = new LinearLayout(this);
            llRowsAns[rowNo].setOrientation(LinearLayout.HORIZONTAL);
			/*
			 * LinearLayout.LayoutParams llp = (LayoutParams)
			 * llRowsAns[rowNo].getLayoutParams(); llp.width = llp.MATCH_PARENT;
			 */
            llRowsAns[rowNo].setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, rowHeight));
            //llRowsAns[rowNo].setBackgroundColor(Color.CYAN);
            llAns.addView(llRowsAns[rowNo]);

            int colWidth = llAns.getWidth() / totalColNo;
            Log.d("vishal", "onStart colWidth " + colWidth);
            colNo = 0;
            while (colNo < totalColNo) {
                String key;
                if(keySetIterator.hasNext()) {
                    String str = keySetIterator.next();
                    hmAns.put(str, tvNo);
                    key = getTvAnsStr(tvNo, str);
                } else
                    key = "";
                tvAns[tvNo] = new TextView(this);
                tvAns[tvNo].setOnClickListener(wordClickListener);

                tvAns[tvNo].setText(key);
                tvAns[tvNo].setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
               // tvAns[tvNo].setAlpha(.8f);
               // tvAns[tvNo].setBackgroundColor(Color.WHITE);
                tvAns[tvNo].setTextColor(Color.WHITE);
                tvAns[tvNo].setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT,
                        1.f));
                tvAns[tvNo].setWidth(colWidth);
                tvAns[tvNo].setGravity(Gravity.CENTER);
                // tvAns[tvNo].setHeight(rowHeight);
                tvAns[tvNo].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15.f);
                llRowsAns[rowNo].addView(tvAns[tvNo]);
                tvNo++;
                colNo++;
            }




            rowNo++;
        }
        enableTvAnsClicking(false);
        Set<String> keySet1 = hmAns.keySet();
        Iterator<String> keySetIterator1 = keySet1.iterator();
        while (keySetIterator1.hasNext()) {
            String key = keySetIterator1.next();
            Log.d("vishal", "key: " + key + " value:" + hmAns.get(key));
        }
        // Create adapter to set value for grid view
		/*ArrayList<String> ansList= new ArrayList<String>();
		Set<String> keySet = hmAns.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			   String key = keySetIterator.next();
			  ansList.add(key);
			}
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ansList);
		//hexsLayout.setBackgroundColor(Color.YELLOW);
        gvAns.setAdapter(adapter);*/
    }

    private void enableTvAnsClicking(boolean b) {
        for(int i=0; i<tvAns.length; i++) {
            tvAns[i].setClickable(b);
        }
    }

    private void setAnwsersTexts() {
		/*for(int i=0; i<tvAnsStatus.length; i++) {
			String key = getTvAnsStr(i, tvAns[i].getText().toString());
			tvAns[i].setText(key);
		}*/
        Set<String> keySet1 = hmAns.keySet();
        Iterator<String> keySetIterator1 = keySet1.iterator();
        while (keySetIterator1.hasNext()) {
            String key = keySetIterator1.next();
            String key1 = getTvAnsStr(hmAns.get(key), key);
            tvAns[hmAns.get(key)].setText(key1);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();



    }

    private void play(){
        header.setScore(0);
        if(timeMode) {
            //header.setTimerVal(1000*60*3);
            //header.startTimer();
            millisecLeft = 1000*60*3;
            isTimerRunning = true;
            startTimer();
        }
        spellAlgo.play();
        hexsLayout.setChar(3, mainCh);
        shuffle(restStr);
    }


    private void shuffle(String restStr) {
        for(int i=0; i<6; i++){
            if(i<3) {
                hexsLayout.setChar(i, restStr.charAt(i));
            }else{
                hexsLayout.setChar(i+1, restStr.charAt(i));
            }
        }
    }

    public char getMainCh() {
        return mainCh;
    }
    public void setMainCh(char mainCh) {
        this.mainCh = mainCh;
        Log.d("vishal", "game setMainCh "+mainCh);
    }
    public String getRestStr() {
        return restStr;
    }
    public void setRestStr(String restStr) {
        this.restStr = restStr;
        Log.d("vishal", "game setRestStr "+restStr);
    }
    public HashMap<String, Integer> getHmAns() {
        return hmAns;
    }
    public void setHmAns(HashMap<String, Integer> hmAns) {
        this.hmAns = hmAns;
        Log.d("vishal", "game setHmAns "+hmAns.toString());
    }
    public void addChar(char ch) {
        // TODO Auto-generated method stub
        //
        wordArea.add(ch);
        String str = wordArea.getText();
        if(hmAns.containsKey(str)) {
            int in = hmAns.get(str);
            if(tvAnsStatus[in] != STATUS_SHOW) {
                tvAnsStatus[in]= STATUS_SHOW;
                tvAns[in].setText(str);
                wordArea.setText("");
                addScore(str);
                music.playSound(R.raw.success);
            }
        }
    }

    public void addScore(String str) {
        // TODO Auto-generated method stub
        //
        switch (str.length()) {
            case 4:
                header.setScore(header.getScore() + LOWEST_WORD_SCORE);
                break;
            case 5:
                header.setScore(header.getScore() + LOWEST_WORD_SCORE*2);
                break;
            case 6:
                header.setScore(header.getScore() + LOWEST_WORD_SCORE*3);
                break;
            case 7:
                header.setScore(header.getScore() + LOWEST_WORD_SCORE*4);
                break;
        }
    }


    public void setTvAnsStatus(int status) {
        int len = tvAnsStatus.length;
        for (int i = 0; i < len; i++) {
            if (status == STATUS_SHOW)
                tvAnsStatus[i] = status;
            else if (status == STATUS_HINT && tvAnsStatus[i] != STATUS_SHOW)
                tvAnsStatus[i] = status;
            else if (status == STATUS_HIDE && (tvAnsStatus[i] != STATUS_SHOW
                    || tvAnsStatus[i] != STATUS_HINT)) {
                tvAnsStatus[i] = status;
            }
        }
    }


    public String getTvAnsStr(int i, String str) {
        if (tvAnsStatus[i] == STATUS_HIDE) {
            int len = str.length();
            String hidden = "";
            for (int j = 0; j < len; j++) {
                hidden += "*";
            }
            return hidden;
        } else if (tvAnsStatus[i] == STATUS_SHOW) {
            return str;
        } else if (tvAnsStatus[i] == STATUS_HINT) {
            int len = str.length();
            String hint = "";


            int place1 = randomno.nextInt(len);
            int place2 = randomno.nextInt(len);
            if (len == 4 || len == 5) {
                for (int j = 0; j < len; j++) {
                    if (j == place1) {
                        hint += str.charAt(j);
                    } else {
                        hint += "*";
                    }
                }
            } else if (len == 6 || len == 7) {
                for (int j = 0; j < len; j++) {
                    if (j == place1 || j == place2) {
                        hint += str.charAt(j);
                    } else {
                        hint += "*";
                    }
                }
            }


            return hint;
        }
        return str;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();


            // moveTaskToBack(false);


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void timerUp() {
        // TODO Auto-generated method stub
        showAnswers();
    }




    protected void exitByBackKey() {


		/*AlertDialog alertbox = new AlertDialog.Builder(this)
				.setMessage("Do you really want to exit Spellathon Master?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {


							// do something when the button is clicked
							public void onClick(DialogInterface arg0, int arg1) {


								finish();
								// close();


							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {


					// do something when the button is clicked
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();*/
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.quit_dialog);

        Button buttonYes = (Button) dialog.findViewById(R.id.button_quit_dialog_yes);
        Button buttonNo = (Button) dialog.findViewById(R.id.button_quit_dialog_no);
        //header.cancelTimer();
        // if button is clicked, close the custom dialog
        buttonYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                dialog.dismiss();
                // header.cancelTimer();
                if (timeMode) {
                    gameTimer.cancel();
                }
                showAnswers();
            }
        });
        buttonNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //header.resumeTimer();
            }
        });
        dialog.show();
    }


    protected void showAnswers() {
        // TODO Auto-generated method stub
        final Dialog answerDialog = new Dialog(this);
        answerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        answerDialog.setContentView(R.layout.answers);

        Button buttonHome = (Button) answerDialog.findViewById(R.id.button_answers_home);
        Button buttonNextGame = (Button)answerDialog.findViewById(R.id.button_answers_next_game);
        final LinearLayout answers = (LinearLayout)answerDialog.findViewById(R.id.ll1_answers);


        enableTvAnsClicking(true);
        Set<String> keySet = hmAns.keySet();
        Iterator<String> keySetIterator = keySet.iterator();
        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            int wordIndex = hmAns.get(key);
            if(tvAnsStatus[wordIndex] == STATUS_SHOW) {
                tvAns[wordIndex].setTextColor(Color.parseColor("#006400"));
            } else {
                tvAns[wordIndex].setTextColor(Color.RED);
            }
            tvAns[hmAns.get(key)].setText(key);
        }
        //((LinearLayout)findViewById(R.id.relativelayout_main)).removeAllViews();
        ViewGroup parent = (ViewGroup) (llAns.getParent());
        if(parent != null) {
            parent.removeView(llAns);
        }
        setTvAnsStatus(STATUS_SHOW);
        llAns.setBackgroundColor(Color.WHITE);
        answers.addView(llAns);
        buttonHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                //dialog.dismiss();
                answerDialog.dismiss();
                //header.cancelTimer();
                finish();
                overridePendingTransition(0, 0);
            }
        });
        buttonNextGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                answerDialog.cancel();
                answerDialog.dismiss();
                answers.removeView(llAns);
                //header.cancelTimer();
                ((LinearLayout)findViewById(R.id.relativelayout_main)).addView(llAns);

                init();
                gameStart();
            }
        });
        answerDialog.setCanceledOnTouchOutside(false);
        answerDialog.setCancelable(false);
        answerDialog.show();
    }

    //*********timer logic**************
    public void startTimer() {
        // TODO Auto-generated method stub
        final DateFormat df = new DateFormat();
        final Time t = new Time();
        gameTimer = new CountDownTimer(millisecLeft, 1000) {
            long min, sec;


            public void onTick(long millisUntilFinished) {
                //t.set(millisUntilFinished/1000);
                // tvTimer.setText(timerText + t.minute+":"+t.second);
                sec = millisUntilFinished/1000;
                min = sec/60;
                sec = sec - min*60;
                millisecLeft = millisUntilFinished;
                header.setTimerVal(min, sec);
                //here you can have your logic to set text to edittext
            }


            public void onFinish() {
                header.setTimerVal(-1, -1);
                showAnswers();
                //gameTimer.cancel();
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
        startTimer();
        //}
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public void playSound(int resoId) {
        if(wordArea.getCharCount()<8) {
            music.playLongSound(resoId);
        }
    }
}


package com.wishall.spellathon;

/**
 * Created by Harshita on 03-07-2016.
 */
import android.R.string;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class WordArea extends RelativeLayout{

    private Game game;
    private TextView tvword;
    private ImageView ivcross;
    public WordArea(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }


    public WordArea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init();
    }

    public WordArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        inflate(getContext(), R.layout.wordarea, this);

        tvword = (TextView) findViewById(R.id.textView_words);
        ivcross = (ImageView) findViewById(R.id.imageView_cross);
		/*LinearLayout.LayoutParams crossParams = (LinearLayout.LayoutParams) ivcross.getLayoutParams();
		crossParams.width = ivcross.getHeight();
		ivcross.setLayoutParams(crossParams);*/

        ivcross.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String text = tvword.getText().toString();
                if(text.length()==0)
                    return;
                tvword.setText(text.substring(0, text.length() - 1));
                game.playSound(R.raw.click);
            }
        });
        ivcross.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                String text = tvword.getText().toString();
                if(text.length()==0)
                    return true;
                tvword.setText("");
                game.playSound(R.raw.click1);
                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        RelativeLayout.LayoutParams crossParams = (RelativeLayout.LayoutParams) ivcross.getLayoutParams();
        crossParams.width = ivcross.getHeight();
        ivcross.setLayoutParams(crossParams);
        ivcross.setVisibility(View.VISIBLE);
        //tvword.setWidth(this.getWidth()-ivcross.getWidth());
        //tvword.setTextSize(tvword.getHeight());
    }

    public void add(char ch){
        tvword.setText(tvword.getText().toString()+ch);

    }
    private void clear() {
        tvword.setText("");
    }


    public void setGame(Game game2) {
        // TODO Auto-generated method stub
        this.game = game2;
    }

    public String getText() {
        return tvword.getText().toString();
    }

    public void setText(String str) {
        tvword.setText(str);
    }

    public int getCharCount() {
        return tvword.getText().length();
    }

}


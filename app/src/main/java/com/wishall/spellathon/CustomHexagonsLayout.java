package com.wishall.spellathon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomHexagonsLayout extends RelativeLayout{

    private static final int HexCount = 7;
    private Game game;
    private HexagonImageView imageViewHexaGons[];
    private ImageView imageViewHexaGon, imageViewHexaGon1, imageViewHexaGon2, imageViewHexaGon3, imageViewHexaGon4, imageViewHexaGon5, imageViewHexaGon6;
    private RelativeLayout relHexView, relHexView1, relHexView2, relHexView3,relHexView4,relHexView5,relHexView6, relRectangle;
    private TextView textView, textView1, textView2, textView3, textView4, textView5, textView6;
    int width, gaps, hexWidth;

    public CustomHexagonsLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public CustomHexagonsLayout(Context context,  AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public CustomHexagonsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        inflate(getContext(), R.layout.hexagons_layout, this);
        imageViewHexaGons = new HexagonImageView[7];

        //setBackgroundColor(Color.YELLOW);

        initViews();




    }

    private void initViews() {
        // TODO Auto-generated method stub
        relHexView = (RelativeLayout)findViewById(R.id.relative_hexagon);
        relHexView1 = (RelativeLayout)findViewById(R.id.relative_hexagon1);
        relHexView2 = (RelativeLayout)findViewById(R.id.relative_hexagon2);
        relHexView3 = (RelativeLayout)findViewById(R.id.relative_hexagon3);
        relHexView4 = (RelativeLayout)findViewById(R.id.relative_hexagon4);
        relHexView5 = (RelativeLayout)findViewById(R.id.relative_hexagon5);
        relHexView6 = (RelativeLayout)findViewById(R.id.relative_hexagon6);
        relRectangle = (RelativeLayout)findViewById(R.id.relative_rectangle);

		/*imageViewHexaGon=(ImageView)findViewById(R.id.imageView_hexagon);
        imageViewHexaGon1=(ImageView)findViewById(R.id.imageView_hexagon1);
        imageViewHexaGon2=(ImageView)findViewById(R.id.imageView_hexagon2);
        imageViewHexaGon3=(ImageView)findViewById(R.id.imageView_hexagon3);
        imageViewHexaGon4=(ImageView)findViewById(R.id.imageView_hexagon4);
        imageViewHexaGon5=(ImageView)findViewById(R.id.imageView_hexagon5);
        imageViewHexaGon6=(ImageView)findViewById(R.id.imageView_hexagon6);*/

        imageViewHexaGons[0]=(HexagonImageView)findViewById(R.id.imageView_hexagon);
        imageViewHexaGons[1]=(HexagonImageView)findViewById(R.id.imageView_hexagon1);
        imageViewHexaGons[2]=(HexagonImageView)findViewById(R.id.imageView_hexagon2);
        imageViewHexaGons[3]=(HexagonImageView)findViewById(R.id.imageView_hexagon3);
        imageViewHexaGons[4]=(HexagonImageView)findViewById(R.id.imageView_hexagon4);
        imageViewHexaGons[5]=(HexagonImageView)findViewById(R.id.imageView_hexagon5);
        imageViewHexaGons[6]=(HexagonImageView)findViewById(R.id.imageView_hexagon6);


        textView = (TextView)findViewById(R.id.textview1);
        textView1 = (TextView)findViewById(R.id.textview2);
        textView2 = (TextView)findViewById(R.id.textview3);
        textView3 = (TextView)findViewById(R.id.textview4);
        textView4 = (TextView)findViewById(R.id.textview5);
        textView5 = (TextView)findViewById(R.id.textview6);
        textView6 = (TextView)findViewById(R.id.textview7);


       // Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.back_hex3);

        /*imageViewHexaGon.setImageBitmap(icon);
        imageViewHexaGon1.setImageBitmap(icon);
        imageViewHexaGon2.setImageBitmap(icon);
        imageViewHexaGon3.setImageBitmap(icon);
        imageViewHexaGon4.setImageBitmap(icon);
        imageViewHexaGon5.setImageBitmap(icon);
        imageViewHexaGon6.setImageBitmap(icon);*/

        /*imageViewHexaGons[0].setImageBitmap(icon);
        imageViewHexaGons[1].setImageBitmap(icon);
        imageViewHexaGons[2].setImageBitmap(icon);
        imageViewHexaGons[3].setImageBitmap(icon);
        imageViewHexaGons[4].setImageBitmap(icon);
        imageViewHexaGons[5].setImageBitmap(icon);
        imageViewHexaGons[6].setImageBitmap(icon);*/

        imageViewHexaGons[0].setChar('a');
        imageViewHexaGons[1].setChar('b');
        imageViewHexaGons[2].setChar('c');
        imageViewHexaGons[3].setChar('d');
        imageViewHexaGons[4].setChar('e');
        imageViewHexaGons[5].setChar('f');
        imageViewHexaGons[6].setChar('g');

        for(int i=0; i<HexCount; i++)
            imageViewHexaGons[i].setHexParent(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        //paste your code to get your layout params.
        width = getWidth();
        gaps = width/60;
        hexWidth = (width - 2*gaps)/3;
        // textView.setText(gaps+" "+width+" "+hexWidth);

        setHexMargins(0, 0, hexWidth/2+gaps/2, hexWidth);
        setHexMargins(1, 0, hexWidth/2+hexWidth+gaps+gaps/2, hexWidth);
        setHexMargins(2, hexWidth-hexWidth/4+gaps, 0, hexWidth);
        setHexMargins(3, hexWidth-hexWidth/4+gaps, hexWidth + gaps, hexWidth);
        setHexMargins(4, hexWidth-hexWidth/4+gaps, (hexWidth + gaps)*2, hexWidth);
        setHexMargins(5, 2*hexWidth-hexWidth/2+2*gaps, hexWidth/2+gaps/2, hexWidth);
        setHexMargins(6, 2*hexWidth-hexWidth/2+2*gaps, hexWidth/2+hexWidth + gaps+gaps/2, hexWidth);
        //setHexMargins(7, hexWidth/4, hexWidth/2+gaps/2, 2*hexWidth+gaps);

        LayoutParams lphex7 = (RelativeLayout.LayoutParams)relRectangle.getLayoutParams();
        lphex7.topMargin = hexWidth/4;
        lphex7.leftMargin = hexWidth/2+gaps/2;
        lphex7.width = 2*hexWidth+gaps;
        lphex7.height = 2*hexWidth+(int)(2*gaps);
        relRectangle.setLayoutParams(lphex7);

        //relHexView.setBackgroundColor(Color.GREEN);
        //relHexView3.setBackgroundColor(Color.GREEN);
        //relHexView5.setBackgroundColor(Color.GREEN);
        this.setVisibility(View.VISIBLE);
    }

    public void setChar(int hexNo, char ch) {
        if(hexNo == 0) {
            textView.setText(""+ch);
            imageViewHexaGons[0].setChar(ch);
        } else if(hexNo == 1) {
            textView1.setText(""+ch);
            imageViewHexaGons[1].setChar(ch);
        } else if(hexNo == 2) {
            textView2.setText(""+ch);
            imageViewHexaGons[2].setChar(ch);
        }else if(hexNo == 3) {
            textView3.setText(""+ch);
            //Log.d("vishal", ""+(textView3==null));
            imageViewHexaGons[3].setChar(ch);
        }else if(hexNo == 4) {
            textView4.setText(""+ch);
            imageViewHexaGons[4].setChar(ch);
        }else if(hexNo == 5) {
            textView5.setText(""+ch);
            imageViewHexaGons[5].setChar(ch);
        }else if(hexNo == 6) {
            textView6.setText(""+ch);
            imageViewHexaGons[6].setChar(ch);
        }
    }

    private void setHexMargins(int layoutNo, int topMargin, int leftMargin, int width) {
        if(layoutNo == 0) {
            LayoutParams lphex0 = (RelativeLayout.LayoutParams)relHexView.getLayoutParams();
            lphex0.topMargin = topMargin;
            lphex0.leftMargin = leftMargin;
            lphex0.width = width;
            lphex0.height = width;
            relHexView.setLayoutParams(lphex0);
            //  textView.setText(relHexView2.getWidth()+" "+relHexView2.getLeft());
        } else if(layoutNo == 1) {
            LayoutParams lphex1 = (RelativeLayout.LayoutParams)relHexView1.getLayoutParams();
            lphex1.topMargin = topMargin;
            lphex1.leftMargin = leftMargin;
            lphex1.width = width;
            lphex1.height = width;
            relHexView1.setLayoutParams(lphex1);
            // textView1.setText(relHexView1.getWidth()+" "+relHexView1.getLeft());
        } else if(layoutNo == 2) {
            LayoutParams lphex2 = (RelativeLayout.LayoutParams)relHexView2.getLayoutParams();
            lphex2.topMargin = topMargin;
            lphex2.leftMargin = leftMargin;
            lphex2.width = width;
            lphex2.height = width;
            relHexView2.setLayoutParams(lphex2);
            // textView2.setText(relHexView2.getWidth()+" "+relHexView2.getLeft());
        }else if(layoutNo == 3) {
            LayoutParams lphex3 = (RelativeLayout.LayoutParams)relHexView3.getLayoutParams();
            lphex3.topMargin = topMargin;
            lphex3.leftMargin = leftMargin;
            lphex3.width = width;
            lphex3.height = width;
            relHexView3.setLayoutParams(lphex3);
            // textView3.setText(relHexView3.getWidth()+" "+relHexView3.getLeft());
        }else if(layoutNo == 4) {
            LayoutParams lphex4 = (RelativeLayout.LayoutParams)relHexView4.getLayoutParams();
            lphex4.topMargin = topMargin;
            lphex4.leftMargin = leftMargin;
            lphex4.width = width;
            lphex4.height = width;
            relHexView4.setLayoutParams(lphex4);
            //  textView4.setText(relHexView4.getWidth()+" "+relHexView4.getLeft());
        }else if(layoutNo == 5) {
            LayoutParams lphex5 = (RelativeLayout.LayoutParams)relHexView5.getLayoutParams();
            lphex5.topMargin = topMargin;
            lphex5.leftMargin = leftMargin;
            lphex5.width = width;
            lphex5.height = width;
            relHexView5.setLayoutParams(lphex5);
            // textView5.setText(relHexView5.getWidth()+" "+relHexView5.getLeft());
        }else if(layoutNo == 6) {
            LayoutParams lphex6 = (RelativeLayout.LayoutParams)relHexView6.getLayoutParams();
            lphex6.topMargin = topMargin;
            lphex6.leftMargin = leftMargin;
            lphex6.width = width;
            lphex6.height = width;
            relHexView6.setLayoutParams(lphex6);
            // textView2.setText(relHexView2.getWidth()+" "+relHexView2.getLeft());
        }else if(layoutNo == 7) {

            // textView2.setText(relHexView2.getWidth()+" "+relHexView2.getLeft());
        }
    }
    //called when hexagon is clicked
    public void addChar(char ch) {
        game.addChar(ch);
    }

    public void setGame(Game game) {
        // TODO Auto-generated method stub
        this.game = game;
    }

    public void playSound(int resoId) {
        game.playSound(resoId);
    }
}

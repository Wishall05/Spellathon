package com.wishall.spellathon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;

public class BackSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private DrawClock drawClock;
    private Bitmap bubbles[];
    private int bubblesX[];
    private int bubblesY[];
    private int bubblesSize[];
    private int bubblesSpeed[];
    private  int minBubbleSize, maxBubbleSize, maxBubbleSpeed;
    private static final int bubbleCount = 9;
    private int screenWidth, screenHeight;
    private static Random randomno;
    private Bitmap picture;

    public BackSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);
        init();
    }

    public BackSurfaceView(Context context) {
        super(context);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);
        init();
    }

    public BackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);
        init();
    }

    private void init() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
        Log.d("vishal", "screen width="+screenWidth+" height="+screenHeight);
        randomno = new Random();
        Resources res = getContext().getResources();
        picture = BitmapFactory.decodeResource(res, R.drawable.bubble);

        minBubbleSize = (int)convertDpToPixel(5, getContext());
        maxBubbleSize = (int)convertDpToPixel(130, getContext());
        maxBubbleSpeed = 3;
        bubblesSize = new int[bubbleCount];
        bubblesSpeed = new int[bubbleCount];
        bubblesX = new int[bubbleCount];
        bubblesY = new int[bubbleCount];
        bubbles= new Bitmap[bubbleCount];


        //picture = Bitmap.createScaledBitmap(picture, 100, 100, true);
        for(int i=0; i<bubbleCount; i++) {
            bubblesSize[i] = randomno.nextInt(maxBubbleSize) + minBubbleSize;
            bubblesSpeed[i] = randomno.nextInt(maxBubbleSpeed) + 1;
            bubblesX[i] = randomno.nextInt(screenWidth+bubblesSize[i])-bubblesSize[i];
            bubblesY[i] = randomno.nextInt(screenHeight+bubblesSize[i])-bubblesSize[i];
            bubbles[i] = Bitmap.createScaledBitmap(picture, bubblesSize[i], bubblesSize[i], true);
            Log.d("vishal", "bubble"+i+" width="+bubblesSize[i]);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //this.getHolder().setFormat(PixelFormat.TRANSPARENT);
        getHolder().addCallback(this);
        drawClock = new DrawClock(getHolder(), getResources());
        drawClock.setRunning(true);
        drawClock.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        drawClock.setRunning(false);
        while (retry) {
            try {
                drawClock.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

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

    class DrawClock extends Thread{
        private boolean runFlag = false;
        private SurfaceHolder surfaceHolder;

        private Matrix matrix;
        private long prevTime;
        private Paint painter;

        public DrawClock(SurfaceHolder surfaceHolder, Resources resources){
            this.surfaceHolder = surfaceHolder;

            this.surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);


            matrix = new Matrix();
            this.painter=new Paint();
            this.painter.setStyle(Paint.Style.FILL);

        }

        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (runFlag) {

                matrix.preRotate(1.0f, picture.getWidth() / 2, picture.getHeight() / 2);
                canvas = null;
                try {
                    //surfaceHolder.getSurface().setAlpha(0.5f);
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        if(canvas != null)
                            canvas.drawColor( 0, PorterDuff.Mode.CLEAR );

                        for(int i=0; i<bubbleCount; i++) {
                            if(canvas != null) {
                                canvas.drawBitmap(bubbles[i], bubblesX[i], bubblesY[i], this.painter);
                                bubblesY[i] -= bubblesSpeed[i];
                                if(bubblesY[i] < (0 - bubblesSize[i])) {
                                    bubblesSize[i] = randomno.nextInt(maxBubbleSize) + minBubbleSize;
                                    bubblesX[i] = randomno.nextInt(screenWidth);
                                    bubblesY[i] = screenHeight + bubblesSize[i];
                                    bubblesSpeed[i] = randomno.nextInt(maxBubbleSpeed) + 1;
                                    bubbles[i] = null;
                                    bubbles[i] = Bitmap.createScaledBitmap(picture, bubblesSize[i], bubblesSize[i], true);
                                }
                            }
                        }

                    }
                }
                finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}


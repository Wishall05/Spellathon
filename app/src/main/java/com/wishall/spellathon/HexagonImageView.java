package com.wishall.spellathon;

/**
 * Created by Vishal on 14-06-2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HexagonImageView extends ImageView {


    private CustomHexagonsLayout parent;


    char ch;

    public HexagonImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();

        Bitmap roundBitmap = getRoundedCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

              /*Point point1_draw = new Point(75, 0);
              Point point2_draw = new Point(0, 50);
              Point point3_draw = new Point(0, 100);
              Point point4_draw = new Point(75, 150);
              Point point5_draw = new Point(150, 100);
              Point point6_draw = new Point(150, 50);
*/
        Point point1_draw = new Point(radius/2, 0);
        Point point2_draw = new Point(0, radius/4);
        Point point3_draw = new Point(0, 3*radius/4);
        Point point4_draw = new Point(radius/2, radius);
        Point point5_draw = new Point(radius, 3*radius/4);
        Point point6_draw = new Point(radius, radius/4);

        Path path = new Path();
        path.moveTo(point1_draw.x, point1_draw.y);
        path.lineTo(point2_draw.x, point2_draw.y);
        path.lineTo(point3_draw.x, point3_draw.y);
        path.lineTo(point4_draw.x, point4_draw.y);
        path.lineTo(point5_draw.x, point5_draw.y);
        path.lineTo(point6_draw.x, point6_draw.y);

        path.close();
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BBFF99"));
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // paint.setXfermode(new PorterDuffXfermode(Mode.DARKEN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);
        return output;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        int w = getWidth();
        int r = w/2;
        float cx = r;
        float cy = r;
        float x = event.getX();
        float y = event.getY();
        float dist = (float) Math.sqrt((x-cx)*(x-cx)+(y-cy)*(y-cy));
        Log.d("vishal", ""+this.getChar()+" "+r+" "+dist+" "+cx+" "+cy+" "+x+" "+y);
        if(dist<r) {

            parent.addChar(this.getChar());
            parent.playSound(R.raw.click2);
           /* MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.click);
            mp.start();*/
        }
        return super.onTouchEvent(event);
    }

    public CustomHexagonsLayout getHexParent() {
        return parent;
    }

    public void setHexParent(CustomHexagonsLayout parent) {
        this.parent = parent;
    }


    public char getChar() {
        return ch;
    }

    public void setChar(char ch) {
        this.ch = ch;
    }
}

package com.tp.myapplication;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


/**
 * Created by yoann on 09/10/2018.
 * classe permettant de faire le dessin d'un cercle qu'on pourra déplacer
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder = null;

    public MySurfaceView(Context context) {
        super(context);

        setFocusable(true);

        if(surfaceHolder == null) {
            // Get surfaceHolder object.
            surfaceHolder = getHolder();
            // Add this as surfaceHolder callback object.
            surfaceHolder.addCallback(this);
        }



        // Set the parent view background color. This can not set surfaceview background color.
        this.setBackgroundColor(Color.CYAN);

        // Set current surfaceview at top of the view tree.
        this.setZOrderOnTop(true);

        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }


    /* This method will be invoked to draw a circle in canvas. */
    public void drawRedBall(float circleX, float circleY)
    {
        // Get and lock canvas object from surfaceHolder.
        Canvas canvas = surfaceHolder.lockCanvas();


        // Draw the surfaceview background color.
        Paint surfaceBackground = new Paint();
        surfaceBackground.setColor(Color.CYAN);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), surfaceBackground);

        // Draw the circle.
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(circleX, circleY, 100, paint);
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),circleX,circleY,paint);

        // Unlock the canvas object and post the new draw.
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
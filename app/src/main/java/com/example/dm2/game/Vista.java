package com.example.dm2.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class Vista extends SurfaceView {

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite sprite;

    public Vista(Context context) {
        super(context);

        gameLoopThread = new GameLoopThread(this);
        holder=getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry){
                    try{
                        gameLoopThread.join();
                        retry = false;
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        });
       bmp=BitmapFactory.decodeResource(getResources(),R.drawable.esqueleto2);
        sprite=new Sprite(this,bmp);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        sprite.onDraw(canvas);
    }
}



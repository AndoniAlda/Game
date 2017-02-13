package com.example.dm2.game;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
    static final long FPS = 10;
    private Vista vista;
    private boolean running = false;


    public GameLoopThread (Vista vista){
        this.vista= vista;
    }

    public void setRunning(boolean run) {
        this.running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running){
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = vista.getHolder().lockCanvas();
                synchronized (vista.getHandler()){
                    vista.onDraw(c);
                }
            }
            finally {
                if (c!=null){
                    vista.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() -startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            }catch (Exception e){}

        }
    }

}

package com.example.dm2.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Sprite {
    int[] DIRECTION_TO_ANIMATION_MAP={1,2,3,4};
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 9;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private Vista vista;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int ySpeed;


    public Sprite(Vista vista,Bitmap bmp) {
        this.vista = vista;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        Random rnd = new Random();
        xSpeed=rnd.nextInt(25);
        ySpeed=rnd.nextInt(25);
    }

    private void update(){
        if (x >= vista.getWidth() - width - xSpeed ||x+xSpeed<=0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y>=vista.getHeight() - height - ySpeed || y+ ySpeed <=0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;

    }

    public void onDraw (Canvas canvas){
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow()* height;
        Rect src = new Rect (srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect (x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow()
    {
        double dirDouble = (Math.atan2(xSpeed,ySpeed)/(Math.PI/2)+2);
        int direction = (int)Math.round(dirDouble)%BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

}

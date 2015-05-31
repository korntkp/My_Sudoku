package com.zackoji.mysudoku;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by Zackoji on 31/5/2558.
 */
public class PuzzleView extends View{
    private final PuzzleActivity game;

    private float width;
    private float height;
    private int selX;
    private int selY;
    private final Rect selRect = new Rect();

    public PuzzleView(Context context){
        super(context);
        this.game = (PuzzleActivity) context;
        setFocusable(true);//?
        setFocusableInTouchMode(true);//?
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = w/9f;
        height = h/9f;
        getReact(selX, selY, selRect);
    }

    private void getReact(int x, int y, Rect rect) {
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw Background
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.puzzle_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

    }
}

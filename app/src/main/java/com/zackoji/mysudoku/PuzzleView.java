package com.zackoji.mysudoku;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zackoji on 31/5/2558.
 */
public class PuzzleView extends View {
    private final PuzzleActivity game;

    private float width;
    private float height;
    private int selX;
    private int selY;
    private final Rect selRect = new Rect();

    public PuzzleView(Context context) {
        super(context);
        this.game = (PuzzleActivity) context;
        setFocusable(true);//?
        setFocusableInTouchMode(true);//?
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9f;
        height = h / 9f;
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

        //Draw Grid
        //Set Color
        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark));
        Paint highlight = new Paint();
        highlight.setColor(getResources().getColor(R.color.puzzle_highlight));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));
        //Draw Sub Grid
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, light);// ----
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, highlight);
            canvas.drawLine(i * width, 0, i * width, getHeight(), light);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), highlight);
        }
        //Draw Primary Grid
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0) continue;
            canvas.drawLine(0, i * height, getWidth(), i * height, dark);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), dark);
        }

        //Draw Number
        Paint fg = new Paint(Paint.ANTI_ALIAS_FLAG);
        fg.setColor(getResources().getColor(R.color.puzzle_foreground));
        fg.setStyle(Paint.Style.FILL);
        fg.setTextSize(height * 0.75f);
        fg.setTextScaleX(width / height);
        fg.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = fg.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getTileString(i, j), i * width + x, j * height + y, fg);
            }
        }

        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));
        //selected.setAlpha(64);
        canvas.drawRect(selRect, selected);

        // Draw Hint
        int c[] = {getResources().getColor(R.color.puzzle_hint_0),
                getResources().getColor(R.color.puzzle_hint_1),
                getResources().getColor(R.color.puzzle_hint_2)};

        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int numLeft = 9 - game.getUsedTiles(i,j).length;
                if(numLeft < c.length){
                    Rect r = new Rect();
                    getReact(i, j, r);
                    Paint hint = new Paint();
                    hint.setColor(c[numLeft]);
                    canvas.drawRect(r, hint);
                }
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                select(selX, selY - 1);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                select(selX, selY + 1);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                select(selX - 1, selY);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                select(selX + 1, selY);
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() != MotionEvent.ACTION_DOWN) return super.onTouchEvent(event);

        select((int) (event.getX() / width), (int) (event.getY() / height));
        //select((int) (event.getX()), (int) (event.getY()));
        Log.w("Sudoku","X = " + event.getX() + " Y = " + event.getY());
        Log.w("Sudoku","width = " + width + " height = " + height);
        //Log.
        game.showKeypadOrError(selX, selY);
        return true;
    }

    public void setSelectdTile(int num){
        if(game.setTileIfValid(selX, selY, num)){
            invalidate();
        }
    }

    private void select(int x, int y) {
        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(x, 0), 8);
        getReact(selX, selY, selRect);
        invalidate(selRect);
    }
}


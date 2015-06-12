package com.zackoji.mysudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Zackoji on 12/6/2558.
 */
public class Keypad extends Dialog {
    protected static final String TAG = "Sudoku";

    private final View keys[] = new View[10];
    private final int used[];
    private final PuzzleView puzzleView;

    public Keypad(Context context, int used[], PuzzleView puzzleView){
        super(context);
        this.used = used;
        this.puzzleView = puzzleView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setTitle();
    }
}

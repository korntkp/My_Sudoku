package com.zackoji.mysudoku;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Zackoji on 31/5/2558.
 */
public class PuzzleActivity extends Activity{

    public static final String KEY_DIFFICULT = "difficulty";
    public static final int DIFFICULT_EASY = 0;
    public static final int DIFFICULT_MEDIUM = 1;
    public static final int DIFFICULT_HARD = 2;

    private int puzzle[] = new int[9*9];

    private PuzzleView puzzleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int diff = getIntent().getIntExtra(KEY_DIFFICULT, DIFFICULT_EASY);
        //puzzle= getPuzzle(diff);
        //calculateUsedTiles();

        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
    }

    public String getTileString(int x, int y) {
        int v = getTile(x, y);

        if(v == 0) return "1";
        else return String.valueOf(v);

    }

    private int getTile(int x, int y) {
        return puzzle[y * 9 + x];
    }

    public void showKeypadOrError(int selX, int selY) {

    }

    public boolean setTileIfValid(int selX, int selY, int num) {
        return false;
    }
}

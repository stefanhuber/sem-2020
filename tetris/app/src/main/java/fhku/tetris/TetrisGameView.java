package fhku.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TetrisGameView extends View {

    protected static final String TAG = "TetrisGameView";
    protected int cellSize = 0;
    protected int lines = 0;
    protected int[][] board;

    protected Paint paintBackground;
    protected Paint paintBlock;

    public TetrisGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.WHITE);
        paintBlock = new Paint();
        paintBlock.setColor(Color.GREEN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        cellSize = MeasureSpec.getSize(widthMeasureSpec) / 10;
        lines = MeasureSpec.getSize(heightMeasureSpec) / cellSize;
        Log.i(TAG, "onMeasure - Cellsize: " + cellSize + ", Lines: " + lines);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getLines() {
        return lines;
    }

    public void draw(int[][] board) {
        this.board = board;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        canvas.drawRect(0, 0, w, h, paintBackground);

        if (board == null) {
            return;
        }

        for (int line = 0; line < board.length; line++) {
            for (int column = 0; column < 10; column++) {
                if (board[line][column] > 0) {
                    int left = column * cellSize;
                    int right = left + cellSize;
                    int top = line * cellSize;
                    int bottom = top + cellSize;

                    canvas.drawRect(left, top, right, bottom, paintBlock);
                    // canvas.drawCircle(cx, cy, radius, paintBlock);
                }
            }
        }
    }
}

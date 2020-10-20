package fhku.tetris;

import java.util.Random;

public class Tetris {

    protected int[][] board;
    protected int currentBlockLine = -1;
    protected int currentBlockColumn = -1;

    public void init(int lines) {
        board = new int[lines][10];

        for (int line = 0; line < board.length; line++) {
            for (int column = 0; column < 10; column++) {
                board[line][column] = -1;
            }
        }
    }

    public boolean isInitialized() {
        return board != null;
    }

    public int[][] getBoard() {
        return board;
    }

    public void left() {
        if (currentBlockLine > -1 && currentBlockColumn > 0) {
            board[currentBlockLine][currentBlockColumn] = -1;
            board[currentBlockLine][--currentBlockColumn] = 1;
        }
    }

    public void right() {
        if (currentBlockLine > -1 && currentBlockColumn < 9) {
            board[currentBlockLine][currentBlockColumn] = -1;
            board[currentBlockLine][++currentBlockColumn] = 1;
        }
    }

    public void next() {
        if (currentBlockLine < 0) {
            currentBlockColumn = (int) Math.floor(Math.random() * 10);
            currentBlockLine = 0;
            board[currentBlockLine][currentBlockColumn] = 1;
        } else {
            board[currentBlockLine][currentBlockColumn] = -1;

            if (currentBlockLine < board.length - 1) {
                currentBlockLine++;
                board[currentBlockLine][currentBlockColumn] = 1;
            } else {
                currentBlockLine = -1;
            }
        }
    }

}

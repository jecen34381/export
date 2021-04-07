package com.example.mq1.bean;

import com.example.mq1.service.ChessRuleInterface;
import com.example.mq1.service.GoBangRuleImpl;

/**
 * 棋盘类
 */
public class CheckerBoard {

    private int width;
    private int height;

    private Chess[][] board = null;


    public CheckerBoard(int width, int height, Chess[][] board) {
        this.width = width;
        this.height = height;
        this.board = new Chess[width][height];
    }
    public void putChess(ChessRuleInterface chessRuleInterface,Chess newChess){
        //验证棋子是否能落入
        chessRuleInterface.rule(this, newChess);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Chess[][] getBoard() {
        return board;
    }

    public void setBoard(Chess[][] board) {
        this.board = board;
    }
}

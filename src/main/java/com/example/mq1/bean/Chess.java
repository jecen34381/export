package com.example.mq1.bean;

/**
 * 棋子
 */
public class Chess {

    private int type;
    private int locationX;
    private int locationY;

    public Chess(int type) {
        this.type = type;
    }

    public Chess(int type, int locationX, int locationY) {
        this.type = type;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
}

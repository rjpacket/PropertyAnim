package com.taovo.rjp.propertyanim.evaluator;

/**
 * @author Gimpo create on 2017/9/5 11:24
 * @email : jimbo922@163.com
 */

public class BookValue {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public BookValue(){}

    public BookValue(int left, int top, int right, int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}

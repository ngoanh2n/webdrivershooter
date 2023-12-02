package com.github.ngoanh2n.wds;

public class Dimension {
    private int w;
    private int h;

    public Dimension(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return String.format("%dx%d", w, h);
    }
}

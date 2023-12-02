package com.github.ngoanh2n.wds;

public class Rectangle {
    private Position position;
    private Dimension dimension;

    public Rectangle(int x, int y, int w, int h) {
        this.position = new Position(x, y);
        this.dimension = new Dimension(w, h);
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getWidth() {
        return dimension.getWidth();
    }

    public int getHeight() {
        return dimension.getHeight();
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public String toString() {
        return String.format("%s %s", position.toString(), dimension.toString());
    }
}

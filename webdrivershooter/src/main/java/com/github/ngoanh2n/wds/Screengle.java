package com.github.ngoanh2n.wds;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Screengle {
    private final Point location;
    private final Dimension size;

    public Screengle(Point location, Dimension size) {
        this.location = location;
        this.size = size;
    }

    //-------------------------------------------------------------------------------//

    public static Screengle from(Dimension size) {
        Point location = new Point(0, 0);
        return from(location, size);
    }

    public static Screengle from(Point location, Dimension size) {
        return new Screengle(location, size);
    }

    //-------------------------------------------------------------------------------//

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }
}

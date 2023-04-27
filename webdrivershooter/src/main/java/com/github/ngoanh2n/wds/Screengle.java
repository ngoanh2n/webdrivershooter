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
}

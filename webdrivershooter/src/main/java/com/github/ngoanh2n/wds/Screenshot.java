package com.github.ngoanh2n.wds;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screenshot {
    private final Color decoratedColor;
    private final Screengle[] ignoredElements;
    private BufferedImage image;
    private BufferedImage decoratedImage;

    public Screenshot(Color decoratedColor, Screengle[] ignoredElements) {
        this.decoratedColor = decoratedColor;
        this.ignoredElements = ignoredElements;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

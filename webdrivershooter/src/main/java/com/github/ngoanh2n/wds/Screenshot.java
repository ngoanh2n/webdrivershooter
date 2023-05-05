package com.github.ngoanh2n.wds;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screenshot {
    private final BufferedImage image;
    private final Color decoratedColor;
    private final Screengle[] decoratedScreengles;

    public Screenshot(BufferedImage image, Color decoratedColor, Screengle[] decoratedScreengles) {
        this.image = image;
        this.decoratedColor = decoratedColor;
        this.decoratedScreengles = decoratedScreengles;
    }

    //-------------------------------------------------------------------------------//

    public BufferedImage getImage() {
        return image;
    }
}

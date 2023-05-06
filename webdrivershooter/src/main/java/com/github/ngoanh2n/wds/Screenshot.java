package com.github.ngoanh2n.wds;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public class Screenshot {
    private final BufferedImage image;
    private final boolean isMasked;
    private final Color maskedColor;
    private final Screengle[] maskedScreengles;
    private BufferedImage maskedImage;
    private Graphics2D maskedGraphics;

    public Screenshot(BufferedImage image, Color maskedColor, Screengle[] maskedScreengles) {
        this.image = image;
        this.isMasked = false;
        this.maskedColor = maskedColor;
        this.maskedScreengles = maskedScreengles;
        this.initializeMaskedImage();
    }

    //-------------------------------------------------------------------------------//

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getMaskedImage() {
        if (!isMasked) {
            if (maskedScreengles.length == 0) {
                maskedImage = image;
            } else {
                for (Screengle maskedScreengle : maskedScreengles) {
                    BufferedImage elementImage = cutImage(maskedScreengle);
                    BufferedImage maskedElementImage = maskImage(elementImage);
                    drawElementOverMaskedImage(maskedScreengle, maskedElementImage);
                }
            }
            maskedGraphics.dispose();
        }
        return maskedImage;
    }

    //-------------------------------------------------------------------------------//

    protected BufferedImage cutImage(Screengle screengle) {
        int x = screengle.getX();
        int y = screengle.getY();
        int w = screengle.getWidth();
        int h = screengle.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;

        BufferedImage eImage = new BufferedImage(w, h, t);
        Graphics graphics = eImage.getGraphics();
        graphics.drawImage(image, x, y, null);
        graphics.dispose();
        return eImage;
    }

    protected BufferedImage maskImage(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(maskedColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
        return image;
    }

    protected void drawElementOverMaskedImage(Screengle screengle, BufferedImage elementImage) {
        maskedGraphics.drawImage(maskedImage, 0, 0, null);
        maskedGraphics.drawImage(elementImage, screengle.getX(), screengle.getY(), null);
    }

    //-------------------------------------------------------------------------------//

    private void initializeMaskedImage() {
        int w = image.getWidth();
        int h = image.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;
        maskedImage = new BufferedImage(w, h, t);
        maskedGraphics = maskedImage.createGraphics();
        maskedGraphics.drawImage(image, 0, 0, null);
    }
}

package com.github.ngoanh2n.wds;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public class Screenshot {
    private final BufferedImage image;
    private final Screengle[] screengles;
    private final Color maskedColor;
    private final boolean isExcepted;
    private final boolean isMasked;
    private BufferedImage maskedImage;
    private Graphics2D maskedGraphics;

    public Screenshot(BufferedImage image, Screengle[] screengles, Color maskedColor) {
        this(image, screengles, maskedColor, false);
    }

    public Screenshot(BufferedImage image, Screengle[] screengles, Color maskedColor, boolean isExcepted) {
        this.image = image;
        this.screengles = screengles;
        this.maskedColor = maskedColor;
        this.isExcepted = isExcepted;
        this.isMasked = false;
        this.initializeMaskedImage();
    }

    //-------------------------------------------------------------------------------//

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getMaskedImage() {
        if (!isMasked) {
            drawMaskedImage();
            if (isExcepted) {
                maskedImage = maskImage(maskedImage);
                for (Screengle screengle : screengles) {
                    BufferedImage elementImage = cutImage(screengle);
                    drawElementOverMaskedImage(elementImage, screengle);
                }
            } else {
                for (Screengle screengle : screengles) {
                    BufferedImage elementImage = cutImage(screengle);
                    elementImage = maskImage(elementImage);
                    drawElementOverMaskedImage(elementImage, screengle);
                }
            }
            disposeMaskedImage();
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
        graphics.drawImage(image, 0, 0, w, h, x, y, w + x, h + y, null);
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

    protected void drawMaskedImage() {
        maskedGraphics.drawImage(image, 0, 0, null);
    }

    protected void disposeMaskedImage() {
        maskedGraphics.dispose();
    }

    protected void drawElementOverMaskedImage(BufferedImage elementImage, Screengle screengle) {
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
    }
}

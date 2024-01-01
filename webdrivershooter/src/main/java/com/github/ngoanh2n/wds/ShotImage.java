package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Dimension;
import com.github.ngoanh2n.ImageUtils;
import com.github.ngoanh2n.Point;
import com.github.ngoanh2n.Rectangle;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import static com.github.ngoanh2n.wds.Shot.Position.Type.L;
import static com.github.ngoanh2n.wds.Shot.Position.Type.S;

/**
 * Merge shot parts and mask rectangles.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/webdrivershooter">ngoanh2n/webdrivershooter</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter">com.github.ngoanh2n:webdrivershooter</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
@CanIgnoreReturnValue
@ParametersAreNonnullByDefault
public class ShotImage {
    private final static Logger log = LoggerFactory.getLogger(ShotImage.class);
    private final ShooterOptions options;
    private final Screener screener;
    private final List<Rectangle> areaRects;
    private final LinkedList<Rectangle> cropRects;
    private final LinkedList<Rectangle> mergeRects;
    private final LinkedList<Rectangle> shotRects;
    private final Dimension size;
    private BufferedImage image;

    /**
     * Construct a new {@link ShotImage}.
     *
     * @param options   {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param size      The size of image for creating the {@link Screenshot}.
     * @param areaRects Areas to mask or ignore to be not masked.
     */
    protected ShotImage(ShooterOptions options, Screener screener, List<Rectangle> areaRects, Dimension size) {
        this.options = options;
        this.screener = screener;
        this.areaRects = areaRects;
        this.cropRects = new LinkedList<>();
        this.mergeRects = new LinkedList<>();
        this.shotRects = new LinkedList<>();
        this.size = size;
        this.image = ImageUtils.create(size);
        log.debug("[{}]", size);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Merge the specified shot part over the screenshot image with its top-left corner at (currentScrollX,currentScrollY).
     *
     * @param shot The shot part to be drawn over the screenshot image and its rectangle.
     */
    protected void merge(Shot shot) {
        solveShotRect(shot);
        mergeShotImage(shot);
        logShotPosition(shot);
    }

    protected ImmutablePair<Shot, List<Rectangle>> getResult() {
        Shot shot = getFinalShot();
        List<Rectangle> rects = getFinalAreaRects();
        return new ImmutablePair<>(shot, rects);
    }

    //-------------------------------------------------------------------------------//

    private void solveShotRect(Shot shot) {
        solveRectToCrop(shot);
        solveRectToMerge(shot);
        shotRects.add(shot.getRect());
    }

    private void mergeShotImage(Shot shot) {
        Rectangle cRect = cropRects.getLast();
        Rectangle mRect = mergeRects.getLast();
        BufferedImage sImage = shot.getImage();
        BufferedImage cImage = ImageUtils.crop(sImage, cRect);
        ImageUtils.drawArea(image, cImage, mRect.getLocation());
    }

    private void logShotPosition(Shot shot) {
        Shot.Position position = shot.getPosition();
        Rectangle sRect = shotRects.getLast();
        Rectangle cRect = cropRects.getLast();
        Rectangle mRect = mergeRects.getLast();
        log.debug("{} -> [{}], [{}], [{}]", position, sRect, cRect, mRect);
    }

    private Shot getFinalShot() {
        Rectangle innerRect = screener.getInnerRect();
        Point location = innerRect.getLocation();
        resizeImage();
        Rectangle rect = new Rectangle(location, size);
        return new Shot(image, rect);
    }

    private List<Rectangle> getFinalAreaRects() {
        int xToDec = shotRects.getFirst().getX();
        int yToDec = shotRects.getFirst().getY();

        for (Rectangle areaRect : areaRects) {
            areaRect.getLocation().decX(xToDec);
            areaRect.getLocation().decY(yToDec);
        }
        return areaRects;
    }

    //-------------------------------------------------------------------------------//

    private void solveRectToCrop(Shot shot) {
        int w = shot.getRect().getWidth();
        int h = shot.getRect().getHeight();

        Point loca = new Point(0, 0);
        Dimension size = new Dimension(w, h);

        int header = screener.getHeader();
        int footer = screener.getFooter();

        Dimension sb = screener.getScrollbar();
        Shot.Position pos = shot.getPosition();

        if (pos.getY() == S || pos.getY() == L || pos.getX() == L) {
            if (pos.getY() == S) {
                size.decH(footer);
            }
            if (pos.getY() == L) {
                int gone = getGoneY(shot);
                loca.incY(gone);
                size.decH(gone);
            }
            if (pos.getX() == L) {
                int gone = getGoneX(shot);
                loca.incX(gone);
                size.decW(gone);
            }
        } else {
            loca.incY(header);
            size.decH(header);
            size.decH(footer);
        }

        if (pos.getX() != L) {
            if (options.shooter() != 1) {
                if (size.getWidth() < this.size.getWidth()) {
                    size.decW(sb.getWidth());
                }
            }
        }
        if (pos.getY() != L) {
            if (options.shooter() == 2 || options.shooter() == 4) {
                size.decH(sb.getHeight());
            }
        }
        cropRects.add(new Rectangle(loca, size));
    }

    private void solveRectToMerge(Shot shot) {
        Shot.Position pos = shot.getPosition();
        Rectangle last = cropRects.getLast();

        Point loca = new Point(0, 0);
        Dimension size = last.getSize();

        if (!pos.isOrigin()) {
            last = mergeRects.getLast();

            if (pos.getY() != S && pos.getX() == S) {
                int lastY = last.getY() + last.getHeight();
                loca.setY(lastY);
            } else if (pos.getX() != S && pos.getY() == S) {
                int lastX = last.getX() + last.getWidth();
                loca.setX(lastX);
            } else if (pos.getY() != S && pos.getX() != S) {
                loca.setY(last.getY());
                loca.setX(last.getX() + last.getWidth());
            }
        }
        mergeRects.add(new Rectangle(loca, size));
    }

    //-------------------------------------------------------------------------------//

    private int getGoneY(Shot shot) {
        if (shot.getPosition().getX() != S) {
            return cropRects.getLast().getY();
        } else {
            Rectangle last = shotRects.getLast();
            int sb = screener.getScrollbar().getHeight();
            int left = shot.getRect().getY() - last.getY();
            return shot.getRect().getHeight() - left - sb;
        }
    }

    private int getGoneX(Shot shot) {
        Rectangle last = shotRects.getLast();
        int sb = screener.getScrollbar().getWidth();
        int left = shot.getRect().getX() - last.getX();
        return shot.getRect().getWidth() - left - sb;
    }

    private void resizeImage() {
        if (options.cutScrollbar()) {
            Dimension sb = screener.getScrollbar();
            Dimension newSize = new Dimension(size);

            newSize.decW(sb.getWidth());
            newSize.decH(sb.getHeight());

            if (!size.equals(newSize)) {
                Rectangle newRect = new Rectangle(newSize);
                image = ImageUtils.crop(image, newRect);
                log.debug("[{}] -> [{}]", size, newSize);
                size.setWidth(newSize.getWidth());
                size.setHeight(newSize.getHeight());
            }
        }
    }
}

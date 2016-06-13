package net.codersgarage.iseeu.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by s4kib on 6/14/16.
 */

public class Utils {
    public static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

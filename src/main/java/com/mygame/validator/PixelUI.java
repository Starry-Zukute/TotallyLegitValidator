package com.mygame.validator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Code-only drawing helpers that skin ordinary Swing components with the sprites.
 * Nothing here needs the designer: they are applied to designer-made components in code.
 */
final class PixelUI {
    private PixelUI() {
    }

    /** Crisp pixel-art scaling. */
    static void hints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    }

    /**
     * Draws a sprite stretched to (x, y, w, h) while keeping its corners (slice x slice pixels) undistorted.
     * @param center false = draw only the frame (corners + edges), leaving the middle untouched
     */
    static void nineSlice(Graphics2D g, BufferedImage img, int x, int y, int w, int h, int slice, boolean center) {
        int sw = img.getWidth(), sh = img.getHeight();
        int s = Math.min(slice, Math.min(sw, sh) / 2);
        int dx = Math.min(s, w / 2), dy = Math.min(s, h / 2);
        hints(g);
        // corners
        g.drawImage(img, x, y, x + dx, y + dy, 0, 0, s, s, null);
        g.drawImage(img, x + w - dx, y, x + w, y + dy, sw - s, 0, sw, s, null);
        g.drawImage(img, x, y + h - dy, x + dx, y + h, 0, sh - s, s, sh, null);
        g.drawImage(img, x + w - dx, y + h - dy, x + w, y + h, sw - s, sh - s, sw, sh, null);
        // edges
        g.drawImage(img, x + dx, y, x + w - dx, y + dy, s, 0, sw - s, s, null);
        g.drawImage(img, x + dx, y + h - dy, x + w - dx, y + h, s, sh - s, sw - s, sh, null);
        g.drawImage(img, x, y + dy, x + dx, y + h - dy, 0, s, s, sh - s, null);
        g.drawImage(img, x + w - dx, y + dy, x + w, y + h - dy, sw - s, s, sw, sh - s, null);
        if (center) {
            g.drawImage(img, x + dx, y + dy, x + w - dx, y + h - dy, s, s, sw - s, sh - s, null);
        }
    }

    /** A pixel-art frame border (e.g. the dialogue box) that only draws the frame, never the middle. */
    static final class NineSliceBorder extends AbstractBorder {
        private final BufferedImage img;
        private final int slice, padding;

        NineSliceBorder(BufferedImage img, int slice, int padding) {
            this.img = img;
            this.slice = slice;
            this.padding = padding;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            nineSlice(g2, img, x, y, w, h, slice, false);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(padding, padding, padding, padding);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(padding, padding, padding, padding);
            return insets;
        }
    }

    /**
     * Skins a normal JProgressBar with a sprite fill and a sprite frame.
     * The frame is drawn at a whole-number pixel scale: its two end caps stay crisp and only a clean
     * middle column is stretched, so the bar can be any width without blowing up the sprite's pixels.
     */
    static final class PixelBarUI extends BasicProgressBarUI {
        private static final int CAP = 3;                 // source pixels kept intact at each end

        private final Supplier<BufferedImage> fill;
        private final BufferedImage frame;
        private final int middleColumn;

        /**
         * @param fill  supplies the fill sprite each time the bar paints (so the colour can follow the value)
         * @param frame the frame sprite (a 1-pixel outline with a hollow middle)
         */
        PixelBarUI(Supplier<BufferedImage> fill, BufferedImage frame) {
            this.fill = fill;
            this.frame = frame;
            this.middleColumn = findCleanColumn(frame);
        }

        /** A column whose inside rows are fully transparent (just the top and bottom outline). */
        private static int findCleanColumn(BufferedImage img) {
            for (int x = img.getWidth() / 2; x < img.getWidth() - CAP; x++) {
                if (isClean(img, x)) {
                    return x;
                }
            }
            for (int x = img.getWidth() / 2; x >= CAP; x--) {
                if (isClean(img, x)) {
                    return x;
                }
            }
            return img.getWidth() / 2;
        }

        private static boolean isClean(BufferedImage img, int x) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                if ((img.getRGB(x, y) >>> 24) > 8) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            JProgressBar bar = (JProgressBar) c;
            int w = c.getWidth(), h = c.getHeight();
            int sw = frame.getWidth(), sh = frame.getHeight();
            int k = Math.max(1, h / sh);                  // whole-number pixel scale
            int fh = sh * k;
            int oy = (h - fh) / 2;                        // centre the frame vertically

            Graphics2D g2 = (Graphics2D) g.create();
            hints(g2);

            // inside of the frame: dark backing, then the fill sprite
            int ix = 2 * k, iy = oy + k, iw = w - 4 * k, ih = fh - 2 * k;
            g2.setColor(new Color(10, 10, 20));
            g2.fillRect(ix, iy, iw, ih);
            int fw = (int) Math.round(iw * bar.getPercentComplete());
            if (fw > 0) {
                g2.drawImage(fill.get(), ix, iy, fw, ih, null);
            }

            // frame: left cap, stretched middle, right cap
            g2.drawImage(frame, 0, oy, CAP * k, oy + fh, 0, 0, CAP, sh, null);
            g2.drawImage(frame, CAP * k, oy, w - CAP * k, oy + fh, middleColumn, 0, middleColumn + 1, sh, null);
            g2.drawImage(frame, w - CAP * k, oy, w, oy + fh, sw - CAP, 0, sw, sh, null);

            if (bar.isStringPainted() && bar.getString() != null) {
                g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                String text = bar.getString();
                int tx = (w - fm.stringWidth(text)) / 2;
                int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2.setColor(Color.BLACK);
                g2.drawString(text, tx + 1, ty + 1);
                g2.setColor(Color.WHITE);
                g2.drawString(text, tx, ty);
            }
            g2.dispose();
        }
    }
}

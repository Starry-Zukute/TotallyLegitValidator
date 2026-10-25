package com.mygame.validator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/** Loads and caches the PNG sprites in src/main/resources/assets/images. */
final class Assets {
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private Assets() {
    }

    /** The image exactly as stored (/assets/images/{name}.png). */
    static synchronized BufferedImage image(String name) {
        return CACHE.computeIfAbsent(name, n -> {
            try (InputStream in = Assets.class.getResourceAsStream("/assets/images/" + n + ".png")) {
                if (in == null) {
                    throw new IOException("missing image " + n);
                }
                return ImageIO.read(in);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    /** The image cropped to its non-transparent pixels (the sprites sit on 64x64 canvases). */
    static synchronized BufferedImage trimmed(String name) {
        BufferedImage cached = CACHE.get("trim:" + name);
        if (cached != null) {
            return cached;
        }
        BufferedImage src = image(name);
        int minX = src.getWidth(), minY = src.getHeight(), maxX = -1, maxY = -1;
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                if ((src.getRGB(x, y) >>> 24) > 8) {
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        BufferedImage result = maxX < 0 ? src : src.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
        CACHE.put("trim:" + name, result);
        return result;
    }

    /** A trimmed sprite scaled (crisp, nearest-neighbour) to the given height, for button/label icons. */
    static ImageIcon icon(String name, int height) {
        BufferedImage src = trimmed(name);
        int width = Math.max(1, Math.round(src.getWidth() * (height / (float) src.getHeight())));
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = out.createGraphics();
        PixelUI.hints(g);
        g.drawImage(src, 0, 0, width, height, null);
        g.dispose();
        return new ImageIcon(out);
    }
}

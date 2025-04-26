package swing.ui.utils;

import core.Variables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class IconLoader implements Variables {

    /**
     * Загружает изображение из ресурсов и масштабирует его под все нужные размеры.
     *
     * @param ICON_PATH путь внутри JAR, например "/icon/app.png"
     * @param sizes        массив сторон (px), например {16,32,48}
     * @return список Image для установки в JFrame#setIconImages
     * @throws IOException при ошибке чтения ресурса
     */

    private static int[] sizes = new int[]{16, 32, 48, 64, 128, 256, 512, 1024};

    public static List<Image> loadAndScaleIcons() throws IOException {
        BufferedImage original = ImageIO.read(Objects.requireNonNull(IconLoader.class.getResourceAsStream(ICON_PATH)));
        List<Image> icons = new ArrayList<>(sizes.length);
        for (int sz : sizes) {
            BufferedImage scaled = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(original, 0, 0, sz, sz, null);
            g.dispose();
            icons.add(scaled);
        }
        return icons;
    }
}

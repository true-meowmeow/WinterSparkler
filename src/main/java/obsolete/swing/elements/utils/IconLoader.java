package obsolete.swing.elements.utils;

import core.config.CoreProperties;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class IconLoader {

    private static final int[] SIZES = {16, 32, 48, 64, 128, 256, 512, 1024};

    private IconLoader() {
    }

    public static List<Image> loadAndScaleIcons() throws IOException {
        String iconPath = CoreProperties.get().iconPath();

        BufferedImage original;
        try (InputStream stream = openIconStream(iconPath)) {
            original = ImageIO.read(stream);
        }
        if (original == null) {
            throw new IOException("Unable to decode icon resource: " + iconPath);
        }

        List<Image> icons = new ArrayList<>(SIZES.length);
        for (int size : SIZES) {
            BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = scaled.createGraphics();
            try {
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.drawImage(original, 0, 0, size, size, null);
            } finally {
                graphics.dispose();
            }
            icons.add(scaled);
        }
        return icons;
    }

    private static InputStream openIconStream(String iconPath) throws IOException {
        InputStream stream = IconLoader.class.getResourceAsStream(iconPath);
        if (stream != null) {
            return stream;
        }

        // Fallback for running from sources without processed resources on the classpath
        String normalized = iconPath.startsWith("/") ? iconPath.substring(1) : iconPath;
        Path devPath = Path.of("src", "main", "resources").resolve(Path.of(normalized));
        if (Files.exists(devPath)) {
            return Files.newInputStream(devPath);
        }

        throw new IOException("Icon resource not found. Expected on classpath as '" + iconPath
                + "' or at " + devPath.toAbsolutePath());
    }
}

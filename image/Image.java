package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
public class Image {

    private static final int FIRST_PRIME = 17;
    private static final int SECOND_PRIME = 31;
    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Image)) {
            return false;
        }
        Image otherImage = (Image) other;
        if (width != otherImage.getWidth() || height != otherImage.getHeight()) {
            return false;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!pixelArray[i][j].equals((otherImage.getPixel(i, j)))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = FIRST_PRIME;
        result = SECOND_PRIME * result + width;
        result = SECOND_PRIME * result + height;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result = SECOND_PRIME * result + pixelArray[i][j].hashCode();
            }
        }

        return result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray.length, pixelArray[0].length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

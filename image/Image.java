package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image representing an image. This class provides methods to read,
 * create, manipulate, and save images.
 *
 * @author Dan Nirel
 */
public class Image {

    /**
     * A prime number used in the hash code calculation.
     */
    private static final int FIRST_PRIME = 17;

    /**
     * Another prime number used in the hash code calculation.
     */
    private static final int SECOND_PRIME = 31;

    /**
     * The two-dimensional array representing the pixels of the image.
     */
    private final Color[][] pixelArray;

    /**
     * The width of the image in pixels.
     */
    private final int width;

    /**
     * The height of the image in pixels.
     */
    private final int height;

    /**
     * Constructs an Image object by reading an image file.
     *
     * @param filename the filename of the image file.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Constructs an Image object with a specified pixel array, width, and height.
     *
     * @param pixelArray the two-dimensional array representing the pixels of the image.
     * @param width      the width of the image in pixels.
     * @param height     the height of the image in pixels.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Compares this image with another object for equality.
     *
     * @param other the object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
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

    /**
     * Generates a hash code for the image.
     *
     * @return the hash code value for this image.
     */
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

    /**
     * Gets the width of the image in pixels.
     *
     * @return the width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image in pixels.
     *
     * @return the height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the color of the pixel at the specified coordinates.
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the color of the pixel.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to a file with the specified file name.
     *
     * @param fileName the name of the file to save the image to.
     */
    public void saveImage(String fileName) {
        BufferedImage bufferedImage = new BufferedImage(pixelArray.length, pixelArray[0].length,
                BufferedImage.TYPE_INT_RGB);
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

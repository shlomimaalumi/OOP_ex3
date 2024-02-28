package ascii_art;

import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

import static java.lang.Math.*;

/**
 * The AlgorithmParameters class holds parameters and methods related to ASCII art generation algorithms. It
 * provides functionality for setting resolution, loading images, and accessing character matchers.
 */
public class Alogithmparameters {

    /**
     * The initial set of characters used for ASCII conversion.
     */
    private static final char[] INIT_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * The default path to the image file.
     */
    private static final String DEFAULT_PATH = "cat.jpeg";

    /**
     * The default resolution for image processing.
     */
    private static final int DEFAUL_RESOLUTION = 128;

    /**
     * The base used for calculating the highest power of 2.
     */
    private static final int BASE_TWO = 2;

    /**
     * The minimum power used for calculating the highest power of 2.
     */
    private static final int MIN_POW = 1;

    /**
     * The character matcher used for matching sub-images to ASCII characters.
     */
    private final SubImgCharMatcher charMatcher;

    /**
     * The resolution for image processing.
     */
    private int resolution;

    /**
     * The image being processed.
     */
    private Image image;

    /**
     * Constructs an AlgorithmParameters instance with default settings.
     *
     * @throws IOException if an error occurs while loading the default image.
     */
    Alogithmparameters() throws IOException {
        try {
            this.image = openImage(DEFAULT_PATH);
        } catch (IOException e) {
            throw new IOException(e);
        }
        resolution = DEFAUL_RESOLUTION;
        charMatcher = new SubImgCharMatcher(INIT_CHARS);
    }

    /**
     * Calculates the highest power of 2 less than or equal to the given number.
     *
     * @param num the input number.
     * @return the highest power of 2 less than or equal to the input number.
     */
    private int getHighestPow2(int num) {
        return MIN_POW << (int) Math.ceil(Math.log(num) / Math.log(BASE_TWO));
    }

    /**
     * Opens an image file located at the specified path.
     *
     * @param path the path to the image file.
     * @return the loaded image.
     * @throws IOException if an error occurs while loading the image.
     */
    private Image openImage(String path) throws IOException {
        Image image;
        try {
            image = new Image(path);
        } catch (IOException e) {
            throw new IOException(e);
        }
        return image;
    }

    /**
     * Increases the resolution by a factor of 2 if possible.
     *
     * @return true if the resolution is successfully increased, false otherwise.
     */
    boolean resUp() {
        if (resolution * BASE_TWO > getHighestPow2(image.getWidth())) {
            return false;
        }
        resolution *= BASE_TWO;
        return true;
    }

    /**
     * Decreases the resolution by a factor of 2 if possible.
     *
     * @return true if the resolution is successfully decreased, false otherwise.
     */
    boolean resDown() {
        if (resolution / BASE_TWO < max(MIN_POW,
                getHighestPow2(image.getWidth()) / getHighestPow2(image.getHeight()))) {
            return false;
        }
        resolution /= BASE_TWO;
        return true;
    }

    /**
     * Updates the current image with a new image loaded from the specified path.
     *
     * @param path the path to the new image file.
     * @throws IOException if an error occurs while loading the new image.
     */
    void updateImage(String path) throws IOException {
        try {
            image = openImage(path);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Gets the current resolution setting.
     *
     * @return the resolution setting.
     */
    int getResolution() {
        return resolution;
    }

    /**
     * Gets the current image being processed.
     *
     * @return the current image.
     */
    Image getImage() {
        return image;
    }

    /**
     * Gets the character matcher used for matching sub-images to ASCII characters.
     *
     * @return the character matcher.
     */
    SubImgCharMatcher getCharMatcher() {
        return charMatcher;
    }
}

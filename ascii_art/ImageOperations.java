package ascii_art;

import image.Image;

import java.awt.*;


/**
 * The ImageOperations class provides operations for processing images and generating ASCII art. This class
 * includes methods for converting images to grayscale, resizing, dividing into sub-images, and calculating
 * brightness values.
 */
public class ImageOperations {

    //region CONSTANT VARIABLES

    /**
     * The ratio of red color in brightness calculation.
     */
    private static final float RED_RATIO = 0.2126f;

    /**
     * The ratio of green color in brightness calculation.
     */
    private static final float GREEN_RATIO = 0.7152f;

    /**
     * The ratio of blue color in brightness calculation.
     */
    private static final float BLUE_RATIO = 0.0722f;

    /**
     * The maximum brightness value for ASCII conversion.
     */
    private static final float MAX_ASCII = 255;

    /**
     * The color value for white.
     */
    private static final int WHITE = 255;

    /**
     * The base for calculating the closest power of 2.
     */
    private static final int BASE_TWO = 2;

    /**
     * represent the move 1 left of the list index
     */
    private static final int LAST_INDEX_MOVE = 1;

    /**
     * Index of the first character in an array or string.
     */
    private static final int FIRST_INDEX = 0;

    /**
     * init a counter value to 0
     */
    private static final int INITIAL_COUNTER = 0;

    /**
     * constant represent half value divide
     */
    private static final int HALF_VAL = 2;

    //endregion


    //region API

    /**
     * Calculates grayscale brightness values for each sub-image of the given image with the specified
     * resolution.
     *
     * @param image      the input image.
     * @param resolution the resolution for dividing the image into sub-images.
     * @return a 2D array containing grayscale brightness values for each sub-image.
     */
    public static float[][] greyBrightnessesByResolution(Image image, int resolution) {
        // Resize the image to the nearest power of 2
        image = resize(image);
        // Divide the resized image into sub-images
        Image[][] subImages = divideImage(image, resolution);
        // Get grayscale brightness values for each sub-image
        return getBrightnessArray(subImages);
    }

    //endregion


    //region PRIVATE METHODS

    /**
     * Finds the closest power of 2 for a given number.
     *
     * @param num the number for which to find the closest power of 2.
     * @return the closest power of 2.
     */
    private static int findClosestPowerOf2(int num) {
        double div = Math.log(num) / Math.log(BASE_TWO);
        int ceilVal = (int) Math.ceil(div);
        return (int) Math.pow(BASE_TWO, ceilVal);
    }

    /**
     * Adds rows of white pixels to the pixel array to match the new height.
     *
     * @param pixelArray the original pixel array.
     * @param height     the original height.
     * @param newHeight  the new height.
     * @param newWidth   the new width.
     */
    private static void addRows(Color[][] pixelArray, int height, int newHeight, int newWidth) {
        for (int i = 0; i < (newHeight - height) / HALF_VAL; i++) {
            for (int j = 0; j < newWidth; j++) {
                pixelArray[i][j] = new Color(WHITE, WHITE, WHITE);
                pixelArray[newHeight - i - LAST_INDEX_MOVE][j] = new Color(WHITE, WHITE, WHITE);
            }
        }
    }

    /**
     * Adds columns of white pixels to the pixel array to match the new width.
     *
     * @param pixelArray the original pixel array.
     * @param width      the original width.
     * @param newWidth   the new width.
     * @param newHeight  the new height.
     */
    private static void addCols(Color[][] pixelArray, int width, int newWidth, int newHeight) {
        for (int i = 0; i < (newWidth - width) / HALF_VAL; i++) {
            for (int j = 0; j < newHeight; j++) {
                pixelArray[j][i] = new Color(WHITE, WHITE, WHITE);
                pixelArray[j][newWidth - i - LAST_INDEX_MOVE] = new Color(WHITE, WHITE, WHITE);
            }
        }
    }

    /**
     * Fills the original image into the resized pixel array.
     *
     * @param image      the original image.
     * @param pixelArray the resized pixel array.
     * @param width      the original width.
     * @param newWidth   the new width.
     * @param height     the original height.
     * @param newHeight  the new height.
     */
    private static void fillOriginalImage(Image image, Color[][] pixelArray, int width, int newWidth,
                                          int height, int newHeight) {
        for (int i = (newHeight - height) / HALF_VAL; i < newHeight - (newHeight - height) / 2; i++) {
            for (int j = (newWidth - width) / HALF_VAL; j < newWidth - (newWidth - width) / 2; j++) {
                pixelArray[i][j] = image.getPixel(i - (newHeight - height) / 2, j -
                        (newWidth - width) / 2);
            }
        }
    }

    /**
     * Resizes the image to the nearest power of 2.
     *
     * @param image the original image.
     * @return the resized image.
     */
    private static Image resize(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = findClosestPowerOf2(width);
        int newHeight = findClosestPowerOf2(height);
        Color[][] pixelArray = new Color[newHeight][newWidth];
        addRows(pixelArray, height, newHeight, newWidth);
        addCols(pixelArray, width, newWidth, newHeight);
        fillOriginalImage(image, pixelArray, width, newWidth, height, newHeight);
        return new Image(pixelArray, newWidth, newHeight);
    }

    /**
     * Calculates grayscale brightness values for each image in a 2D array of images.
     *
     * @param images the 2D array of images.
     * @return a 2D array containing grayscale brightness values for each image.
     */
    private static float[][] getBrightnessArray(Image[][] images) {
        int height = images.length;
        int width = images[FIRST_INDEX].length;
        float[][] brightnessArray = new float[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                brightnessArray[i][j] = getImgBrightness(images[i][j]);
            }
        }
        return brightnessArray;
    }

    /**
     * Calculates the brightness value of a single image.
     *
     * @param image the input image.
     * @return the brightness value of the image.
     */
    private static float getImgBrightness(Image image) {
        float val = INITIAL_COUNTER;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = image.getPixel(i, j);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                float greyVal = red * RED_RATIO + blue * BLUE_RATIO + green * GREEN_RATIO;
                val += greyVal;
            }
        }
        return val / (image.getHeight() * image.getWidth() * MAX_ASCII);
    }

    /**
     * Divides the given image into sub-images with the specified resolution.
     *
     * @param image      the input image.
     * @param resolution the resolution for dividing the image.
     * @return a 2D array of sub-images.
     */
    private static Image[][] divideImage(Image image, int resolution) {
        int resolutionSize = image.getWidth() / resolution;
        int numOfRows = image.getHeight() / resolutionSize;
        Image[][] imageArray = new Image[numOfRows][resolution];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < resolution; j++) {
                imageArray[i][j] = getSubImage(image, i, j, resolutionSize);
            }
        }
        return imageArray;
    }

    /**
     * Retrieves a sub-image from the original image.
     *
     * @param image          the original image.
     * @param x              the row index of the sub-image.
     * @param y              the column index of the sub-image.
     * @param resolutionSize the size of the sub-image.
     * @return the sub-image.
     */
    private static Image getSubImage(Image image, int x, int y, int resolutionSize) {
        int startRow = x * resolutionSize;
        int startCol = y * resolutionSize;
        Color[][] pixelArray = new Color[resolutionSize][resolutionSize];
        for (int i = 0; i < resolutionSize; i++) {
            for (int j = 0; j < resolutionSize; j++) {
                pixelArray[i][j] = image.getPixel(startRow + i, startCol + j);
            }
        }
        return new Image(pixelArray, resolutionSize, resolutionSize);
    }

    //endregion
}

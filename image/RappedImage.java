package image;

import java.awt.*;

class RappedImage {
    //region CONSTANT VARIABLES
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
     * constant represent half value divide
     */
    private static final int HALF_VAL = 2;

    //endregion


    //region API
    /**
     * Resizes the image to the nearest power of 2.
     *
     * @param image the original image.
     * @return the resized image.
     */
    static Image resize(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = findClosestPowerOf2(width);
        int newHeight = findClosestPowerOf2(height);
        Color[][] pixelArray = new Color[newHeight][newWidth];
        rappedImage(image, pixelArray, height, newHeight, newWidth, width);
        return new Image(pixelArray, newWidth, newHeight);
    }
    //endregion


    //region Private helper Methods
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
     * Resizes the image to the nearest power of 2. modify in place the pixel array to match the new size of
     * the image.
     *
     * @param image the original image.
     */
    private static void rappedImage(Image image, Color[][] pixelArray, int height, int newHeight,
                                    int newWidth, int width) {
        addRows(pixelArray, height, newHeight, newWidth);
        addCols(pixelArray, width, newWidth, newHeight);
        fillOriginalImage(image, pixelArray, width, newWidth, height, newHeight);
    }
    //endregion
}

package image;

import java.awt.*;

public class ImageDivider {
    /**
     * Divides the given image into sub-images with the specified resolution.
     *
     * @param image      the input image.
     * @param resolution the resolution for dividing the image.
     * @return a 2D array of sub-images.
     */
    static Image[][] divideImage(Image image, int resolution) {
        int resolutionSize = image.getWidth() / resolution;
        int numOfRows = image.getHeight() / resolutionSize;
        Image[][] subImagesArray = new Image[numOfRows][resolution];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < resolution; j++) {
                subImagesArray[i][j] = getSubImage(image, i, j, resolutionSize);
            }
        }
        return subImagesArray;
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
}

package image;

import static image.ImageBrightnessCalculator.getImgBrightness;
import static image.ImageDivider.divideImage;
import static image.RappedImage.resize;


/**
 * A facade class for calculating grayscale brightness values for sub-images of an image.
 * The class provides a single method for calculating grayscale brightness values for each sub-image
 * of the given image with the specified resolution.
 * this class is a facade class that provides a single method for calculating grayscale brightness values
 * for each sub-image of the given image with the specified resolution.
 */
public class ImageToBrightnessesFacade {

    //region CONSTANT VARIABLES
    /**
     * Index of the first character in an array or string.
     */
    private static final int FIRST_INDEX = 0;
    //endregion


    //region API

    /**
     * Calculates grayscale brightness values for each sub-image of the given image with the specified
     * resolution.
     *
     * @param image       the input image.
     * @param resolution  the resolution for dividing the image.
     * @return a 2D array containing grayscale brightness values for each sub-image.
     */
    public static float[][] greyBrightnessesByResolution(Image image, int resolution) {
        // Resize the image to the nearest power of 2, to ensure that it can be divided into sub-images
        // of the specified resolution
        image = resize(image);
        // Divide the resized image into sub-images with the specified resolution and get grayscale
        // brightness values for each sub-image.
        Image[][] subImages = divideImage(image, resolution);
        // Get grayscale brightness values for each sub-image and return the 2D array.
        return getBrightnessArray(subImages);
    }
    //endregion
    //region PRIVATE METHODS
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
    //endregion

}

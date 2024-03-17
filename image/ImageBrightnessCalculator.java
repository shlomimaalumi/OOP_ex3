package image;

import java.awt.*;

public class ImageBrightnessCalculator {
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
     * init a counter value to 0
     */
    private static final int INITIAL_COUNTER = 0;


    //endregion

    /**
     * Calculates the brightness value of a single image.
     *
     * @param image the input image.
     * @return the brightness value of the image.
     */
    static float getImgBrightness(Image image) {
        float val = INITIAL_COUNTER;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = image.getPixel(i, j);
                float greyVal = getGreyVal(color);
                val += greyVal;
            }
        }
        return val / (image.getHeight() * image.getWidth() * MAX_ASCII);
    }


    /**
     * Calculates the grayscale value of a given color.
     *
     * @param color the input color.
     * @return the grayscale value of the color.
     */
    private static float getGreyVal(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return red * RED_RATIO + blue * BLUE_RATIO + green * GREEN_RATIO;
    }
}

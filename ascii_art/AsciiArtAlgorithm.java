package ascii_art;

import image.Image;
import image.ImageToBrightnessesFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * The AsciiArtAlgorithm class is responsible for generating ASCII art from an image. It uses brightness
 * values calculated from the image and a resolution setting to determine ASCII characters for each pixel.
 */
public class AsciiArtAlgorithm {
    //region CLASS VARIABLES
    /**
     * A map to cache brightness arrays computed for each image-resolution pair.
     */
    private static final Map<Pair, float[][]> brightnessMap = new HashMap<>();

    /**
     * The parameters controlling the ASCII art generation algorithm.
     */
    private final AlogithmParameters alogithmParameters;

    //endregion


    //region API

    /**
     * Constructs an AsciiArtAlgorithm instance with the specified parameters.
     *
     * @param alogithmParameters the algorithm parameters controlling image processing and ASCII art generation.
     */
    public AsciiArtAlgorithm(AlogithmParameters alogithmParameters) {
        this.alogithmParameters = alogithmParameters;
    }

    /**
     * Runs the ASCII art generation algorithm on the specified image with the configured parameters.
     *
     * @return a 2D array representing the ASCII art generated from the image.
     */
    public char[][] run() {
        Pair imageVsResolution = new Pair(alogithmParameters.getImage(), alogithmParameters.getResolution());
        float[][] brightnessArray;

        // Check if brightness array for this image-resolution pair is already computed
        if (brightnessMap.containsKey(imageVsResolution)) {
            brightnessArray = brightnessMap.get(imageVsResolution);
        } else {
            // Compute brightness array for the image at the specified resolution
            brightnessArray = ImageToBrightnessesFacade.greyBrightnessesByResolution(alogithmParameters.getImage(),
                    alogithmParameters.getResolution());
            // Cache the computed brightness array
            brightnessMap.put(imageVsResolution, brightnessArray);
        }

        return getCharsList(brightnessArray);
    }


    //endregion

    //region PRIVATE METHODS AND CLASSES

    /**
     * A utility class representing a pair of an image and a resolution. Used as a key in the brightnessMap
     * to cache brightness arrays.
     *
     * @param image      The image.
     * @param resolution The resolution.
     */
    private record Pair(Image image, int resolution) {
        /**
         * Constructs a Pair object with the specified image and resolution.
         *
         * @param image      the image.
         * @param resolution the resolution.
         */
        private Pair {
        }

        /**
         * Gets the image from the pair.
         *
         * @return the image.
         */
        @Override
        public Image image() {
            return image;
        }

        /**
         * Gets the resolution from the pair.
         *
         * @return the resolution.
         */
        @Override
        public int resolution() {
            return resolution;
        }

        /**
         * Indicates whether some other object is "equal to" this one. This method overrides the default
         * implementation provided by the {@code Object} class. It checks if the specified object is an
         * instance of {@code Pair}, and if so, compares the image and resolution fields of both objects for
         * equality.
         *
         * @param obj the reference object with which to compare.
         * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Pair other)) {
                return false;
            }

            return image.equals(other.image()) && other.resolution() == resolution;
        }


    }

    /**
     * Gets the parameters controlling the ASCII art generation algorithm.
     *
     * @return the algorithm parameters.
     */
    private char[][] getCharsList(float[][] brightnessArray) {
        char[][] asciiArt = new char[brightnessArray.length][brightnessArray[0].length];

        // Convert brightness values to ASCII characters
        for (int i = 0; i < brightnessArray.length; i++) {
            for (int j = 0; j < brightnessArray[i].length; j++) {
                asciiArt[i][j] = alogithmParameters.getCharMatcher().getCharByImageBrightness(brightnessArray[i][j]);
            }
        }

        return asciiArt;
    }
    //endregion
}

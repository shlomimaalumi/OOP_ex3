package ascii_art;

import image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * The AsciiArtAlgorithm class is responsible for generating ASCII art from an image. It uses brightness
 * values calculated from the image and a resolution setting to determine ASCII characters for each pixel.
 */
public class AsciiArtAlgorithm {

    /**
     * A map to cache brightness arrays computed for each image-resolution pair.
     */
    private static final Map<Pair, float[][]> brightnessMap = new HashMap<>();

    /**
     * The parameters controlling the ASCII art generation algorithm.
     */
    private final Alogithmparameters parameters;

    /**
     * Constructs an AsciiArtAlgorithm instance with the specified parameters.
     *
     * @param parameters the algorithm parameters controlling image processing and ASCII art generation.
     */
    public AsciiArtAlgorithm(Alogithmparameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Runs the ASCII art generation algorithm on the specified image with the configured parameters.
     *
     * @return a 2D array representing the ASCII art generated from the image.
     */
    public char[][] run() {
        Pair imageVsResolution = new Pair(parameters.getImage(), parameters.getResolution());
        float[][] brightnessArray;

        // Check if brightness array for this image-resolution pair is already computed
        if (brightnessMap.containsKey(imageVsResolution)) {
            brightnessArray = brightnessMap.get(imageVsResolution);
        } else {
            // Compute brightness array for the image at the specified resolution
            brightnessArray = ImageOperations.greyBrightnessesByResolution(parameters.getImage(),
                    parameters.getResolution());
            // Cache the computed brightness array
            brightnessMap.put(imageVsResolution, brightnessArray);
        }

        char[][] asciiArt = new char[brightnessArray.length][brightnessArray[0].length];

        // Convert brightness values to ASCII characters
        for (int i = 0; i < brightnessArray.length; i++) {
            for (int j = 0; j < brightnessArray[i].length; j++) {
                asciiArt[i][j] = parameters.getCharMatcher().getCharByImageBrightness(brightnessArray[i][j]);
            }
        }

        return asciiArt;
    }




    private static class Pair {
        private static final int FIRST_PRIME = 17;
        private static final int SECOND_PRIME = 31;
        private final Image image;
        private final int resolution;

        Pair(Image image, int resolution) {
            this.image = image;
            this.resolution = resolution;
        }

        public Image getImage() {
            return image;
        }

        public int getResolution() {
            return resolution;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Pair other)) {
                return false;
            }

            return image.equals(other.getImage()) && other.getResolution() == resolution;
        }

        @Override
        public int hashCode() {
            int result = FIRST_PRIME;
            result = SECOND_PRIME * result + image.hashCode();
            result = SECOND_PRIME * result + resolution;
            return result;
        }
    }

}

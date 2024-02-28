package ascii_art;


import image.Image;

import java.util.HashMap;
import java.util.Map;

public class AsciiArtAlgorithm {
    private static final Map<Pair, float[][]> brightnessMap = new HashMap<>();
    //    private static final char[] INIT_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8',
    //    '9'};
//    private final SubImgCharMatcher A = new SubImgCharMatcher(INIT_CHARS);
//    private int resolution = 128;
//    private Image image;
//
    private final Alogithmparameters parameters;

    public AsciiArtAlgorithm(Alogithmparameters parameters){
        this.parameters = parameters;
    }


//
//    public static AsciiArtAlgorithm getInstance() {
//        if (AsciiArtAlgorithm.asciiArtAlgorithmObject == null) {
//            AsciiArtAlgorithm.asciiArtAlgorithmObject = new AsciiArtAlgorithm();
//        }
//        return AsciiArtAlgorithm.asciiArtAlgorithmObject;
//    }

    public char[][] run()  {

        Pair imageVsResolution = new Pair(parameters.getImage(), parameters.getResolution());
        float[][] brightnessArray;
        if (brightnessMap.containsKey(imageVsResolution)) {
            System.out.println("i know the answer");
            brightnessArray = brightnessMap.get(imageVsResolution);
        } else {
            brightnessArray = ImageOperations.greyBrightnessesByResolution(parameters.getImage(),
                    parameters.getResolution());
            brightnessMap.put(imageVsResolution, brightnessArray);
        }
        char[][] asciiArt = new char[brightnessArray.length][brightnessArray[0].length];

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

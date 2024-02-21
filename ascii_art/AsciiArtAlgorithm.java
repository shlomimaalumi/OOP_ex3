package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import image.Image;
import image.ImageOperations;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;
import java.io.IOException;

public class AsciiArtAlgorithm {
    private static final String path = "examples/board.jpeg";

    public char[][] run() throws IOException {
        float[][] brightnessArray = ImageOperations.greyBrightnessesByResolution(path,2);
        char[][] asciiArt = new char[brightnessArray.length][brightnessArray[0].length];
        SubImgCharMatcher charMatcher = new SubImgCharMatcher(new char[]{'m','o'});
//        asciiArt = ImageOperations.greyResizedSubImages(path, 2);
        for (int i = 0; i < brightnessArray.length; i++) {
            for (int j = 0; j < brightnessArray[i].length; j++) {
                asciiArt[i][j] = charMatcher.getCharByImageBrightness(brightnessArray[i][j]);
            }
        }
        ConsoleAsciiOutput c = new ConsoleAsciiOutput();
        c.out(asciiArt);
        return asciiArt;

    }



    private char[][] convertSubImagesToAscii(Image[][] asciiArt) {
        int height = asciiArt.length;
        int width = asciiArt[0].length;
        char[][] asciiArtChars = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                asciiArt
            }
        }
        return asciiArtChars;
    }

    public static void main(String[] args) {
        AsciiArtAlgorithm A = new AsciiArtAlgorithm();
        try{
        char[][] x = A.run();
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }
}

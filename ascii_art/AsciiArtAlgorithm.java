package ascii_art;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImageOperations;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;
import java.io.IOException;

public class AsciiArtAlgorithm {
    private static final String path = "examples/cat.jpeg";

    public char[][] run() throws IOException {
        char[] asciiChars ={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
//        char[] asciiChars = {
//                ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
//                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
//                '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
//                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_',
//                '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
//                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~'
//        };
//        char[] asciiChars = {'0','1','2','3','4','5','6','7','8','9'};
//        char[] asciiChars = new char[]{'-', 'M', 'T', 'b', 's', '1', 'O', '!', 'm', '{', 'S', 'e', 'P',
//        'u', ' ',
//                '<', 'Y', 'L', 'a', '+', '0', 'K', 'i', '7', '/', '*', 'n', '.', '8', 'Z', 'H', '9', ':',
//                '=', 'D', '\'', '>', 'R', '#', ';', 'p', '@', '4', 'y', 'W', 'G', 'C', 'w', '3', 'B', 'X',
//                'E', 'x', 'o', 'N', '&', '"', '$', ',', 'h', 'd', 'F', 'I', 'l', '`', '5', 'c', 'A', 'r',
//                '%', 't','\0'};
//        char[] asciiChars = new char[]{'-', 'M', 'T', 'b', 's', '1', 'O', '!', 'm', '{', 'S', 'e', 'P', ' '
//                , '	', ' ', ' ', '0', '1', '3', '4', '5', '7', '8', '9', 'A', 'B',
//                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'W', 'X',
//                'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'h', 'i', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u',
//                'w', 'x', 'y', '-', 'M', 'T', 'b', 's', '1', 'O', '!', 'm', '{', 'S', 'e', 'P',
//                'u', ' ',
//                '<', 'Y', 'L', 'a', '+', '0', 'K', 'i', '7', '/', '*', 'n', '.', '8', 'Z', 'H', '9', ':',
//                '=', 'D', '\'', '>', 'R', '#', ';', 'p', '@', '4', 'y', 'W', 'G', 'C', 'w', '3', 'B', 'X',
//                'E', 'x', 'o', 'N', '&', '"', '$', ',', 'h', 'd', 'F', 'I', 'l', '`', '5', 'c', 'A', 'r',
//                '%', 't', '\0', 'A', 'B', 'C', 'K', 'T', '`', ' ', '!', 'a', '"', 'c', '$', '&', '\'', '*',
//                '+', ',', 'm', '.', '0', 'p', '1', '4', '7', ':', '{', ';', '=', '@', 'A', 'B', 'C', 'E',
//                'K', 'T', 'W', ' ', 'a', '!', '"', 'c', '%', '&', '+', ',', 'm', '.', 'p', '0', '1', '4',
//                '7', ';', '=','\u0000'};
        float[][] brightnessArray = ImageOperations.greyBrightnessesByResolution(path, 128);
        char[][] asciiArt = new char[brightnessArray.length][brightnessArray[0].length];
        SubImgCharMatcher charMatcher = new SubImgCharMatcher(asciiChars);
//        asciiArt = ImageOperations.greyResizedSubImages(path, 2);
        for (int i = 0; i < brightnessArray.length; i++) {
            for (int j = 0; j < brightnessArray[i].length; j++) {
                asciiArt[i][j] = charMatcher.getCharByImageBrightness(brightnessArray[i][j]);
            }
        }
        HtmlAsciiOutput c = new HtmlAsciiOutput("examples/cat.html", "Courier New");
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
        try {
            char[][] x = A.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class Alogithmparameters {
    private static final char[] INIT_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final String DEFAULT_PATH = "examples/cat.jpeg";
    private static final int DEFAUL_RESOLUTION = 128;
    private final SubImgCharMatcher charMatcher;
    private int resolution;
    private Image image;



    public Alogithmparameters() throws IOException {
        try {
            this.image = openImage(DEFAULT_PATH);
        } catch (IOException e) {
            throw new IOException(e);
        }
        resolution = DEFAUL_RESOLUTION;
        charMatcher = new SubImgCharMatcher(INIT_CHARS);

    }

    private Image openImage(String path) throws IOException {
        Image image;
        try {
            image = new Image(path);
        } catch (IOException e) {
            throw new IOException(e);
        }
        return image;
    }

    void resUp() {

        resolution *= 2;
    }

    void resDown() {
        resolution /= 2;
    }


    void updateImage(String path) throws IOException {
        try {
            image = openImage(path);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    int getResolution() {
        return resolution;
    }

    Image getImage() {
        return image;
    }

    SubImgCharMatcher getCharMatcher() {
        return charMatcher;
    }

}

package image;

import java.awt.*;
import java.io.IOException;

import static java.awt.Color.WHITE;

public class ImageOperations {

    private static final float RED_RATIO = 0.2126f;
    private static final float GREEN_RATIO = 0.7152f;
    private static final float BLUE_RATIO = 0.0722f;
    private static final float MAX_ASCII = 255;

    public static float[][] greyBrightnessesByResolution(String ImagePath, int resolution) throws IOException {
        Image image;
        try {
            image = new Image(ImagePath);
        } catch (IOException e) {
            throw new IOException(e);
        }
        image = resize(image);
        Image[][] subImages = divideImage(image, resolution);
        return getBrightnessArray(subImages);
    }

    private static int findClosestPowerOf2(int num) {
        int powerOf2 = 1;
        while (powerOf2 < num) {
            powerOf2 <<= 1;
        }
        return powerOf2;
    }

    private static void addRows(Color[][] pixelArray, int height, int newHeight, int newWidth) {
        for (int i = 0; i < (newHeight - height) / 2; i++) {
            for (int j = 0; j < newWidth; j++) {
                Color x = new Color(23,23,23);
                pixelArray[i][j] = new Color(WHITE,WHITE,WHITE);
                pixelArray[newHeight - i - 1][j] = new Color(WHITE,WHITE,WHITE);
            }
        }
    }

    private static void addCols(Color[][] pixelArray, int width, int newWidth, int newHeight) {
        for (int i = 0; i < (newWidth - width) / 2; i++) {
            for (int j = 0; j < newHeight; j++) {
                pixelArray[j][i] = new Color(WHITE,WHITE,WHITE);
                pixelArray[j][newWidth - j - 1] = new Color(WHITE,WHITE,WHITE);
            }
        }
    }

    private static void fillOriginalImage(Image image, Color[][] pixelArray, int width, int newWidth,
                                          int height,
                                          int newHeight) {
        for (int i = (newHeight - height) / 2; i < newHeight - (newHeight - height) / 2; i++) {
            for (int j = (newWidth - width) / 2; j < newWidth - (newWidth - width) / 2; j++) {
                pixelArray[i][j] = image.getPixel(i - (newHeight - height) / 2,
                        j - (newWidth - width) / 2);
            }
        }
    }

    private static Image resize(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = findClosestPowerOf2(width);
        int newHeight = findClosestPowerOf2(height);
        Color[][] pixelArray = new Color[newHeight][newWidth];
        addRows(pixelArray, height, newHeight, newWidth);
        addCols(pixelArray, width, newWidth, newHeight);
        fillOriginalImage(image, pixelArray, width, newWidth, height, newHeight);
        return new Image(pixelArray, newWidth, newHeight);
    }


    private static float[][] getBrightnessArray(Image[][] images){
        int height = images.length;
        int width = images[0].length;
        float[][] brightnessArray = new float[height][width];
        for (int i = 0; i < height; i++){
            for (int j=0; j< width;j++){
                brightnessArray[i][j]=getImgBrightness(images[i][j]);
            }
        }
        return brightnessArray;
    }

    private static float getImgBrightness(Image image) {
//        Color[][] pixelArray = new Color[image.getHeight()][image.getWidth()];
        float val = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = image.getPixel(i, j);
                int red = ((Color) color).getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                float greyVal = red * RED_RATIO + blue * BLUE_RATIO + green * GREEN_RATIO;
                val += greyVal;
            }
        }
        return val / (image.getHeight() * image.getWidth() * MAX_ASCII);
    }

    private static Image[][] divideImage(Image image, int resolution) {
        int resolutionSize = image.getWidth() / resolution;
        int numOfRows = image.getHeight() / resolutionSize;
        Image[][] imageArray = new Image[numOfRows][resolution];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < resolution; j++) {
                imageArray[i][j] = getSubImage(image, i, j, resolutionSize);
            }
        }
        return imageArray;
    }

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

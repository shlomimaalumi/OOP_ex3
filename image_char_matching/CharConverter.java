package image_char_matching;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The class converts characters to a binary "image" (2D array of booleans). The CharConverter class converts
 * characters to a binary "image" represented by a 2D array of booleans. It renders a given character,
 * according to how it looks in the specified font, to a square black & white image with a specified pixel
 * resolution.
 */
public class CharConverter {
    //region CONSTANTS

    /**
     * The factor by which to offset the character rendering horizontally.
     */
    private static final double X_OFFSET_FACTOR = 0.2;

    /**
     * The factor by which to offset the character rendering vertically.
     */
    private static final double Y_OFFSET_FACTOR = 0.75;

    /**
     * The default font name to use for character rendering.
     */
    private static final String FONT_NAME = "Courier New";

    /**
     * The default pixel resolution for the rendered image.
     */
    public static final int DEFAULT_PIXEL_RESOLUTION = 16;

    //endregion


    //region API

    /**
     * Renders a given character to a square black & white image (2D array of booleans), with the specified
     * pixel resolution.
     *
     * @param c The character to convert to an image.
     * @return A 2D array of booleans representing the binary image of the character.
     */
    public static boolean[][] convertToBoolArray(char c) {
        BufferedImage img = getBufferedImage(c);
        boolean[][] matrix = new boolean[DEFAULT_PIXEL_RESOLUTION][DEFAULT_PIXEL_RESOLUTION];
        for (int y = 0; y < DEFAULT_PIXEL_RESOLUTION; y++) {
            for (int x = 0; x < DEFAULT_PIXEL_RESOLUTION; x++) {
                matrix[y][x] = img.getRGB(x, y) == 0; //is the color black
            }
        }
        return matrix;
    }

    //endregion


    //region PRIVATE METHODS

    /**
     * Generates a BufferedImage for rendering the specified character with the given font and pixel
     * resolution.
     *
     * @param c The character to render.
     * @return The BufferedImage representing the rendered character.
     */
    private static BufferedImage getBufferedImage(char c) {
        String charStr = Character.toString(c);
        Font font = new Font(CharConverter.FONT_NAME, Font.PLAIN, CharConverter.DEFAULT_PIXEL_RESOLUTION);
        BufferedImage img = new BufferedImage(CharConverter.DEFAULT_PIXEL_RESOLUTION, CharConverter.DEFAULT_PIXEL_RESOLUTION, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setFont(font);
        int xOffset = (int) Math.round(CharConverter.DEFAULT_PIXEL_RESOLUTION * X_OFFSET_FACTOR);
        int yOffset = (int) Math.round(CharConverter.DEFAULT_PIXEL_RESOLUTION * Y_OFFSET_FACTOR);
        g.drawString(charStr, xOffset, yOffset);
        return img;
    }

    //endregion
}

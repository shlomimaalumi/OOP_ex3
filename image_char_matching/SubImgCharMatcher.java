package image_char_matching;
public class SubImgCharMatcher {
    /**
     * Constructor that initializes the character set used by the algorithm.
     *
     * @param charset an array of characters forming the character set for the algorithm.
     */
    public SubImgCharMatcher(char[] charset) {

    }

    /**
     * Retrieves a character from the character set based on the brightness value of a sub-image.
     *
     * @param brightness The brightness value of the sub-image.
     * @return The character from the character set with the closest absolute brightness value.
     *         In case of a tie, returns the character with the lowest ASCII value.
     */
    public char getCharByImageBrightness(double brightness){
        return 'a';
    }

    /**
     * Adds a character to the character set.
     *
     * @param c The character to add.
     */
    public void addChar(char c){

    }

    private boolean updateMin(char c) {
        if (charBrightnessMap.get(c) < charBrightnessMap.get(minCharBrightness) ||
                minCharBrightness == Character.MAX_VALUE) {
            minCharBrightness = c;
            return true;
        }
        return false;
    }

    private boolean updateMax(char c) {
        if (charBrightnessMap.get(c) > charBrightnessMap.get(maxCharBrightness) ||
                maxCharBrightness == Character.MIN_VALUE) {
            maxCharBrightness = c;
            return true;
        }
        return false;
    }



    /**
     * Removes a character from the character set used by the algorithm.
     *
     * @param c The character to be removed from the character set.
     */
    public void removeChar(char c){

    }
}

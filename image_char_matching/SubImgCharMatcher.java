package image_char_matching;

import java.util.*;



public class SubImgCharMatcher {

    private static final char SPACE_CHAR = ' ';


    private final HashMap<Character, Float> charBrightnessMap;
    private final HashMap<Character, Float> charBrightnessNormalMap;
    private char minCharBrightness = Character.MAX_VALUE;
    private char maxCharBrightness = Character.MIN_VALUE;



    /**
     * Constructor that initializes the character set used by the algorithm.
     *
     * @param charset an array of characters forming the character set for the algorithm.
     */
    public SubImgCharMatcher(char[] charset) {
        this.charBrightnessMap = new HashMap<Character, Float>();
        this.charBrightnessNormalMap = new HashMap<Character, Float>();
        for (char c : charset) {
            charBrightnessMap.put(c, getCharBrightness(c));
            updateMin(c);
            updateMax(c);
        }
        initNormalMap();
    }


    /**
     * Retrieves a character from the character set based on the brightness value of a sub-image.
     *
     * @param brightness The brightness value of the sub-image.
     * @return The character from the character set with the closest absolute brightness value. In case of a
     * tie, returns the character with the lowest ASCII value.
     */
    public char getCharByImageBrightness(double brightness) throws RuntimeException {
        if (this.charBrightnessNormalMap.isEmpty()) {
            throw new RuntimeException("set is empty"); // TODO remove it
        }
        double minDis = Float.POSITIVE_INFINITY;
        char retVal = Character.MAX_VALUE;
        for (Map.Entry<Character, Float> pair : charBrightnessNormalMap.entrySet()) {
            double curDis = Math.abs(pair.getValue() - brightness);
            if (curDis < minDis) {
                minDis = curDis;
                retVal = pair.getKey();
            } else if (curDis == minDis) {
                retVal = ((int) retVal < (int) pair.getKey()) ? retVal : pair.getKey();
            }
        }
        return retVal;
    }

    /**
     * Adds a character to the character set.
     *
     * @param c The character to add.
     */
    public void addChar(char c) {
        if (this.charBrightnessNormalMap.containsKey(c)) {
            return;
        }
        if (!this.charBrightnessMap.containsKey(c)) {
            charBrightnessMap.put(c, getCharBrightness(c));
        }
        this.charBrightnessNormalMap.put(c, normalBrightness(c));
        if (updateMin(c) || updateMax(c)) {
            updateNormalMap();
        }
    }

    public List<Character> GetAllKeysSorted() {

        Set<Character> keySet = charBrightnessNormalMap.keySet();

        List<Character> sortedList = new ArrayList<>(keySet);
        Collections.sort(sortedList);

        return sortedList;

    }

    /**
     * Removes a character from the character set used by the algorithm.
     *
     * @param c The character to be removed from the character map.
     */
    public void removeChar(char c) {
        if (!this.charBrightnessMap.containsKey(c)) {
            return;
        }
        this.charBrightnessNormalMap.remove(c);
        if (checkMinUpdate(c) || checkMaxUpdate(c)) {
            updateNormalMap();
        }
    }

    public boolean checkMaxUpdate(char c) {
        if (c == maxCharBrightness) {
            this.maxCharBrightness = Character.MIN_VALUE;
            boolean setFirst = true;
            for (char ch : charBrightnessMap.keySet()) {
                if (setFirst) {
                    minCharBrightness = ch;
                    setFirst = false;
                }
                updateMax(ch);
            }
            return true;
        }
        return false;
    }

    private boolean checkMinUpdate(char c) {
        if (c == minCharBrightness) {
            boolean setFirst = true;
            for (char ch : charBrightnessNormalMap.keySet()) {
                if (setFirst) {
                    minCharBrightness = ch;
                    setFirst = false;
                }
                updateMin(ch);
            }
            return true;
        }
        return false;
    }

    private boolean updateMin(char c) {
        if (minCharBrightness == Character.MAX_VALUE || charBrightnessMap.get(c) < charBrightnessMap.get(minCharBrightness)) {
            minCharBrightness = c;
            return true;
        }
        return false;
    }

    private boolean updateMax(char c) {
        if (maxCharBrightness == Character.MIN_VALUE ||
                charBrightnessMap.get(c) > charBrightnessMap.get(maxCharBrightness)) {
            maxCharBrightness = c;
            return true;
        }
        return false;
    }

    private float getCharBrightness(char c) {
        if (this.charBrightnessMap.containsKey(c)) {
            return this.charBrightnessMap.get(c);
        }
        boolean[][] greyArray = CharConverter.convertToBoolArray(c);
        int count = 0;
        for (boolean[] booleans : greyArray) {
            for (boolean b : booleans) {
                if (b) {
                    count += 1;
                }
            }
        }
        return (float) count / (greyArray.length * greyArray[0].length);
    }

    private float normalBrightness(char c) {
        return (getCharBrightness(c) - getCharBrightness(minCharBrightness)) /
                (getCharBrightness(maxCharBrightness) - getCharBrightness(minCharBrightness));

    }

    private void updateNormalMap() {
        for (Map.Entry<Character, Float> pair : charBrightnessNormalMap.entrySet()) {
            this.charBrightnessNormalMap.put(pair.getKey(), normalBrightness(pair.getKey()));
        }
    }

    private void initNormalMap() {
        for (Map.Entry<Character, Float> pair : charBrightnessMap.entrySet()) {
            this.charBrightnessNormalMap.put(pair.getKey(), normalBrightness(pair.getKey()));
        }
    }


}

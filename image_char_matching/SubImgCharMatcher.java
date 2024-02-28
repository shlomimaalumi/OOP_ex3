package image_char_matching;


import java.util.*;


public class SubImgCharMatcher {

    private static final char SPACE_CHAR = ' ';


    private final HashMap<Character, Float> charBrightnessHistoryMap;
    private final TreeMap<Float, List<Character>> floatToCharsMap;
    private float minCharBrightness = Float.MAX_VALUE;
    private float maxCharBrightness = Float.MIN_VALUE;


    /**
     * Constructor that initializes the character set used by the algorithm.
     *
     * @param charset an array of characters forming the character set for the algorithm.
     */
    public SubImgCharMatcher(char[] charset) {
        this.charBrightnessHistoryMap = new HashMap<>();
        this.floatToCharsMap = new TreeMap<>();
        for (char c : charset) {
            charBrightnessHistoryMap.put(c, getCharBrightness(c));
            updateMin(c);
            updateMax(c);
        }
        initNormalMap();
    }


    public char getCharByImageBrightness(double brightness) throws RuntimeException {
        if (floatToCharsMap.isEmpty()) {
            throw new RuntimeException("Character set is empty");
        }
        if (floatToCharsMap.containsKey((float) brightness)) {
            return asciiMinValue(floatToCharsMap.get((float) brightness));
        }
        Map.Entry<Float, List<Character>> closestBrightnessDown =
                floatToCharsMap.lowerEntry((float) brightness);
        Map.Entry<Float, List<Character>> closestBrightnessUp =
                floatToCharsMap.higherEntry((float) brightness);

        float disFromUp = (closestBrightnessUp != null) ?
                Math.abs(closestBrightnessUp.getKey() - (float) brightness) : Float.MAX_VALUE;
        float disFromDown = (closestBrightnessDown != null) ?
                Math.abs(closestBrightnessDown.getKey() - (float) brightness) : Float.MAX_VALUE;

        if (closestBrightnessUp != null && disFromUp < disFromDown) {
            return asciiMinValue(closestBrightnessUp.getValue());
        } else if (closestBrightnessDown != null) {
            return asciiMinValue(closestBrightnessDown.getValue());
        }
        return SPACE_CHAR;
    }


    private char asciiMinValue(List<Character> characters) {
        char minAsciiChar = characters.get(0);
        for (char c : characters) {
            if (c < minAsciiChar) {
                minAsciiChar = c;
            }
        }
        return minAsciiChar;
    }


    /**
     * Adds a character to the character set.
     *
     * @param c The character to add.
     */
    public void addChar(char c) {
        float cNormalBrightness = normalBrightness(c);
        if (floatToCharsMap.containsKey(cNormalBrightness) &&
                floatToCharsMap.get(cNormalBrightness).contains(c)) {
            return;
        }
        if (!this.charBrightnessHistoryMap.containsKey(c)) {
            charBrightnessHistoryMap.put(c, getCharBrightness(c));
        }
        List<Character> cBrightnessList = floatToCharsMap.get(cNormalBrightness);
        if (cBrightnessList == null) {
            floatToCharsMap.put(cNormalBrightness, new ArrayList<>());
            cBrightnessList = floatToCharsMap.get(cNormalBrightness);
        }
        cBrightnessList.add(c);
        if (updateMin(c) || updateMax(c)) {
            updateNormalMap();
        }
    }

    public List<Character> GetAllCharsSorted() {

        Collection<List<Character>> charSet = floatToCharsMap.values();
        List<Character> flattenedList = new ArrayList<>();

        for (List<Character> sublist : charSet) {
            flattenedList.addAll(sublist);
        }

        List<Character> sortedList = new ArrayList<>(flattenedList);
        Collections.sort(sortedList);

        return sortedList;

    }

    /**
     * Removes a character from the character set used by the algorithm.
     *
     * @param c The character to be removed from the character map.
     */
    public void removeChar(char c) {
        float cNormalBrightness = normalBrightness(c);
        if ((!floatToCharsMap.containsKey(cNormalBrightness)) ||
                !floatToCharsMap.get(cNormalBrightness).contains(c)) {
            //TODO check how to handle
            return;
        }
        List<Character> cBrightnessList = floatToCharsMap.get(normalBrightness(c));
//        cBrightnessList.remove(cBrightnessList.contains(c));
        cBrightnessList.removeIf(element -> Objects.equals(element, c));
        if (cBrightnessList.isEmpty()) {
            floatToCharsMap.remove(normalBrightness(c), cBrightnessList);
        }

        if (checkMinUpdate(c) || checkMaxUpdate(c)) {
            updateNormalMap();
        }
    }

    public boolean checkMaxUpdate(char c) {
        if (charBrightnessHistoryMap.get(c) == maxCharBrightness) {
            maxCharBrightness = floatToCharsMap.lastKey();
            return true;
        }
        return false;
    }

    private boolean checkMinUpdate(char c) {
        if (charBrightnessHistoryMap.get(c) == minCharBrightness) {
            maxCharBrightness = floatToCharsMap.firstKey();
            return true;
        }
        return false;
    }

    private boolean updateMin(char c) {
        if (charBrightnessHistoryMap.get(c) < minCharBrightness) {
            minCharBrightness = charBrightnessHistoryMap.get(c);
            return true;
        }
        return false;
    }

    private boolean updateMax(char c) {
        if (charBrightnessHistoryMap.get(c) > maxCharBrightness) {
            maxCharBrightness = charBrightnessHistoryMap.get(c);
            return true;
        }
        return false;
    }

    private float getCharBrightness(char c) {
        //TODO make this static
        if (this.charBrightnessHistoryMap.containsKey(c)) {
            return this.charBrightnessHistoryMap.get(c);
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
        return (getCharBrightness(c) - minCharBrightness) /
                (maxCharBrightness - minCharBrightness);

    }

    private void updateNormalMap() {
        TreeMap<Float, List<Character>> tempMap = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Float, List<Character>> entry : floatToCharsMap.entrySet()) {
            List<Character> characterList = entry.getValue();
            float newKey = normalBrightness(characterList.get(0));
            tempMap.put(newKey, characterList);
        }

        floatToCharsMap.clear();

        floatToCharsMap.putAll(tempMap);

    }

    private void initNormalMap() {
        for (Map.Entry<Character, Float> pair : charBrightnessHistoryMap.entrySet()) {
            float cNormalBrightness = normalBrightness(pair.getKey());
            List<Character> cBrightnessList = floatToCharsMap.get(cNormalBrightness);
            if (cBrightnessList == null) {
                floatToCharsMap.put(cNormalBrightness, new ArrayList<>());
                cBrightnessList = floatToCharsMap.get(cNormalBrightness);
            }
            cBrightnessList.add(pair.getKey());

        }
    }
}

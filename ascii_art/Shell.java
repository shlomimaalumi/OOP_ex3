package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

import java.io.IOException;
import java.util.List;

public class Shell {
    private static final String OUTPUT_CONSOLE_COMMAND = "output console";
    private static final String OUTPUT_HTML_COMMAND = "output html";
    private static final String OUTPUT_HTML_PATH = "out.html";
    private static final String FONT = "Courier New";


    private static final String PREFIX = ">>> ";
    private static final String ASCII_ART_COMMAND = "asciiArt";
    private static final String EXIT_COMMAND = "exit";
    private static final String ADD_PREFIX = "add";
    private static final String CHANGE_IMAGE_PREFIX = "image";
    private static final String OUTPUT_PREFIX = "output";
    private static final String RES_UP = "res up";
    private static final String RES_DOWN = "res down";
    private static final String REMOVE_PREFIX = "remove";


    private static final int SINGLE_CHAR = 1;
    private static final int FIRST_INDEX = 0;
    private static final int LAST_INDEX = -1;

    private static final int SECOND_INDEX = 1;
    private static final int ATTER_REGEX = 1;
    private static final int TWO_CHARS = 2;


    private static final char SPACE_CHAR = ' ';
    private static final char TILDA_CHAR = '~';
    private static final String ALL_CHARS = "all";
    private static final String SPACE = "space";
    private static final String CHARS = "chars";
    private static final String SPACE_REGEX = " ";
    private static final String MINUS_REGEX = "-";


    private static final String INVALID_ADD_REQUEST = "Did not add due to incorrect format.";
    private static final String INVALID_REMOVE_REQUEST = "Did not remove due to incorrect format.";
    private static final String INVALID_OUTPUT_REQUEST = "Did not change output method due to incorrect " +
            "format.";
    private static final String INVALID_RES_REQUEST = "Did not change resolution due to incorrect format.";
    private static final String RES_EXCEED_WIDTH = "Did not change resolution due to exceeding boundaries.";
    private static final String RES_UPDATE_MESSAGE = "Resolution set to ";
    private static final String IO_EXPECTION = "Did not execute due to problem with image file.";


    private AsciiOutput output;
    private Alogithmparameters alogithmparameters;

    public void run() throws IOException {
        alogithmparameters = new Alogithmparameters();
        AsciiArtAlgorithm algorithm = new AsciiArtAlgorithm(alogithmparameters);
        HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput(OUTPUT_HTML_PATH, FONT);

//        algorithm.run();
        while (true) {
            System.out.print(PREFIX);
            String command = KeyboardInput.readLine();
            if (!handleCommand(command, algorithm)) {
                break;
            }
        }
    }


    private boolean handleCommand(String command, AsciiArtAlgorithm algorithm) {
        switch (command) {
            case EXIT_COMMAND -> {
                return false;
            }
            case RES_UP -> {
                handleResUp();
            }
            case RES_DOWN -> {
                handleResDown();
            }
            case OUTPUT_CONSOLE_COMMAND -> {
                output = new ConsoleAsciiOutput();
            }
            case OUTPUT_HTML_COMMAND -> {
                output = new HtmlAsciiOutput(OUTPUT_HTML_PATH, FONT);
            }
            case ASCII_ART_COMMAND -> {
                runAlgorithm(algorithm);
            }
            case CHARS -> {
                printAllCharsSorted();
            }
            default -> {
                runComplexCommand(command);
            }
        }
        return true;
    }


    private void handleResDown() {
        if (!alogithmparameters.resDown()) {
            System.out.println(RES_EXCEED_WIDTH);
        } else {
            System.out.println(RES_UPDATE_MESSAGE + alogithmparameters.getResolution());
        }
    }

    private void handleResUp() {
        if (!alogithmparameters.resUp()) {
            System.out.println(RES_EXCEED_WIDTH);
        } else {
            System.out.println(RES_UPDATE_MESSAGE + alogithmparameters.getResolution());
        }
    }

    private void printAllCharsSorted() {
        List<Character> sortedList = alogithmparameters.getCharMatcher().GetAllKeysSorted();

        for (int i = 0; i < sortedList.size(); i++) {
            System.out.print(sortedList.get(i));
            if (i != sortedList.size() + LAST_INDEX) {
                System.out.print(SPACE_CHAR);
            }
        }
        System.out.println();
    }


    private void runComplexCommand(String command) {

        if (command.startsWith(ADD_PREFIX)) {
            runAddingCommand(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } else if (command.startsWith(REMOVE_PREFIX)) {
            runRemovingCommand(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } else if (command.startsWith(OUTPUT_PREFIX)) {
            System.out.println(INVALID_OUTPUT_REQUEST);
        } else if (command.startsWith(OUTPUT_PREFIX)) {
            try {
                alogithmparameters.updateImage(command.split(SPACE_REGEX)[ATTER_REGEX]));
            } catch (IOException e){
                System.out.println(IO_EXPECTION);
            }
        }
    }

    private void runAddingCommand(String s) {
        if (s.length() == SINGLE_CHAR) {
            alogithmparameters.getCharMatcher().addChar(s.charAt(FIRST_INDEX));
        } else if (s.equals(ALL_CHARS)) {
            for (char c = SPACE_CHAR; c <= TILDA_CHAR; c++) {
                alogithmparameters.getCharMatcher().addChar(c);
            }
        } else if (s.equals(SPACE)) {
            alogithmparameters.getCharMatcher().addChar(SPACE_CHAR);
        } else {
            String[] sep = s.split(MINUS_REGEX);
            if (sep.length != TWO_CHARS || sep[FIRST_INDEX].length() != SINGLE_CHAR ||
                    sep[SECOND_INDEX].length() != SINGLE_CHAR) {
                System.out.println(INVALID_ADD_REQUEST);
            } else {
                addCharsInRange(sep[FIRST_INDEX].charAt(FIRST_INDEX),
                        sep[SECOND_INDEX].charAt(FIRST_INDEX));
            }
        }
    }

    private void addCharsInRange(char c, char c1) {
        char min_c = ((int) c < (int) c1) ? c : c1;
        char max_c = ((int) c > (int) c1) ? c : c1;
        for (char ch = min_c; ch <= max_c; ch++) {
            alogithmparameters.getCharMatcher().addChar(ch);
        }
    }

    private void runRemovingCommand(String s) {
        if (s.length() == SINGLE_CHAR) {
            System.out.println("heree");
            alogithmparameters.getCharMatcher().removeChar(s.charAt(FIRST_INDEX));
        } else if (s.equals(ALL_CHARS)) {
            for (char c = SPACE_CHAR; c <= TILDA_CHAR; c++) {
                alogithmparameters.getCharMatcher().removeChar(c);
            }
        } else if (s.equals(SPACE)) {
            alogithmparameters.getCharMatcher().removeChar(SPACE_CHAR);
        } else {
            String[] sep = s.split(MINUS_REGEX);
            if (sep.length != TWO_CHARS || sep[FIRST_INDEX].length() != SINGLE_CHAR ||
                    sep[SECOND_INDEX].length() != SINGLE_CHAR) {
                System.out.println(INVALID_REMOVE_REQUEST);
            } else {
                removeCharsInRange(sep[FIRST_INDEX].charAt(FIRST_INDEX),
                        sep[SECOND_INDEX].charAt(FIRST_INDEX));
            }
        }
    }

    private void removeCharsInRange(char c, char c1) {
        char min_c = ((int) c < (int) c1) ? c : c1;
        char max_c = ((int) c > (int) c1) ? c : c1;
        for (char ch = min_c; ch <= max_c; ch++) {
            alogithmparameters.getCharMatcher().removeChar(ch);
        }
    }

    private void runAlgorithm(AsciiArtAlgorithm algorithm) {
        char[][] resultChars = algorithm.run();
        output.out(resultChars);
    }


    public static void main(String[] args) {
        Shell shell = new Shell();
        try {
            shell.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


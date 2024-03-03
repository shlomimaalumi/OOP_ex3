package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

import java.io.IOException;
import java.util.List;


/**
 * The Shell class represents the command-line interface for interacting with the AsciiArtAlgorithm. It
 * allows users to input various commands to manipulate the ASCII art generation process, change settings,
 * and view the output. The class handles user input and delegates the corresponding actions to the
 * AsciiArtAlgorithm and associated components.
 */
public class Shell {
    /**
     * Command string for changing the output mode to console.
     */
    private static final String OUTPUT_CONSOLE_COMMAND = "output console";

    /**
     * Command string for changing the output mode to HTML.
     */
    private static final String OUTPUT_HTML_COMMAND = "output html";

    /**
     * Default path for the HTML output file.
     */
    private static final String OUTPUT_HTML_PATH = "out.html";

    /**
     * Default font used for ASCII art rendering.
     */
    private static final String FONT = "Courier New";

    /**
     * Command string prefix for user input.
     */
    private static final String PREFIX = ">>> ";

    /**
     * Command string for generating ASCII art.
     */
    private static final String ASCII_ART_COMMAND = "asciiArt";

    /**
     * Command string for exiting the shell.
     */
    private static final String EXIT_COMMAND = "exit";

    /**
     * Prefix for commands related to changing the image.
     */
    private static final String CHANGE_IMAGE_PREFIX = "image";

    /**
     * Prefix for commands related to changing the output mode.
     */
    private static final String OUTPUT_PREFIX = "output";

    /**
     * Prefix for commands related to changing the resolution.
     */
    private static final String RES_PREFIX = "res";

    /**
     * Prefix for commands related to adding characters to the charset.
     */
    private static final String ADD_PREFIX = "add";

    /**
     * Prefix for commands related to removing characters from the charset.
     */
    private static final String REMOVE_PREFIX = "remove";

    /**
     * Prefix for commands requiring space separation.
     */
    private static final String SPACE_PREFIX = " ";

    /**
     * Command string for increasing resolution.
     */
    private static final String RES_UP = "res up";

    /**
     * Command string for decreasing resolution.
     */
    private static final String RES_DOWN = "res down";

    /**
     * Length of a single character.
     */
    private static final int SINGLE_CHAR = 1;

    /**
     * Index of the first character in an array or string.
     */
    private static final int FIRST_INDEX = 0;

    /**
     * Index of the last character in an array or string.
     */
    private static final int LAST_INDEX = -1;

    /**
     * Index of the second character in an array or string.
     */
    private static final int SECOND_INDEX = 1;

    /**
     * Regex group index used for splitting commands.
     */
    private static final int ATTER_REGEX = 1;

    /**
     * Number of characters in a range specifier.
     */
    private static final int TWO_CHARS = 2;

    /**
     * Character representing a space.
     */
    private static final char SPACE_CHAR = ' ';

    /**
     * Character representing a tilde (~).
     */
    private static final char TILDA_CHAR = '~';

    /***
     * character representing a dot(.) to print in the console
     * after the resolution is changed.
     * */
    private static final char DOT ='.' ;

    /**
     * Command string representing all printable ASCII characters.
     */
    private static final String ALL_CHARS = "all";

    /**
     * Command string representing the space character.
     */
    private static final String SPACE = "space";

    /**
     * Command string for listing all characters in the charset.
     */
    private static final String CHARS = "chars";

    /**
     * Regular expression for splitting commands with spaces.
     */
    private static final String SPACE_REGEX = " ";

    /**
     * Regular expression for splitting range specifiers.
     */
    private static final String MINUS_REGEX = "-";

    /**
     * Error message for an invalid request to add characters.
     */
    private static final String INVALID_ADD_REQUEST = "Did not add due to incorrect format.";

    /**
     * Error message for an invalid request to remove characters.
     */
    private static final String INVALID_REMOVE_REQUEST = "Did not remove due to incorrect format.";

    /**
     * Error message for an invalid request to change the output mode.
     */
    private static final String INVALID_OUTPUT_REQUEST = "Did not change output method due to incorrect " +
            "format.";

    /**
     * Error message for an invalid request to change the resolution.
     */
    private static final String INVALID_RES_REQUEST = "Did not change resolution due to incorrect format.";

    /**
     * Error message for attempting to change the resolution beyond the image boundaries.
     */
    private static final String RES_EXCEED_WIDTH = "Did not change resolution due to exceeding boundaries.";

    /**
     * Message indicating a successful resolution change.
     */
    private static final String RES_UPDATE_MESSAGE = "Resolution set to ";

    /**
     * Error message for an I/O exception while handling an image file.
     */
    private static final String IO_EXPECTION = "Did not execute due to problem with image file.";

    /**
     * Error message for an invalid command.
     */
    private static final String INVALID_COMMAND = "Did not execute due to incorrect command.";

    /**
     * Error message for attempting to execute a command when the charset is empty.
     */
    private static final String EMPTY_CHARSET_MESSAGE = "Did not execute. Charset is empty.";

    /**
     * Console output handler for ASCII art.
     */
    private static final ConsoleAsciiOutput CONSOLE_ASCII_OUTPUT = new ConsoleAsciiOutput();

    /**
     * HTML output handler for ASCII art.
     */
    private static final HtmlAsciiOutput HTML_ASCII_OUTPUT = new HtmlAsciiOutput(OUTPUT_HTML_PATH, FONT);

    /**
     * Current output mode for ASCII art rendering.
     */
    private AsciiOutput output = CONSOLE_ASCII_OUTPUT;

    /**
     * Parameters and settings for the ASCII art algorithm.
     */
    private AlogithmParameters alogithmparameters;

    /**
     * Constructs a new instance of the Shell class.
     */
    private Shell() {}

    /**
     * Runs the shell, providing a command-line interface for interacting with the ASCII art algorithm.
     */
    public void run() {
        try {
            alogithmparameters = new AlogithmParameters();
        } catch (IOException e) {
            System.out.println(IO_EXPECTION);
        }
        AsciiArtAlgorithm algorithm = new AsciiArtAlgorithm(alogithmparameters);

        while (true) {
            System.out.print(PREFIX);
            String command = KeyboardInput.readLine();
            if (!handleCommand(command, algorithm)) {
                break;
            }
        }
    }

    /**
     * Handles the given command.
     *
     * @param command   The command to handle.
     * @param algorithm The ASCII art algorithm to use.
     * @return {@code true} if the program should continue running; {@code false} if it should exit.
     */
    private boolean handleCommand(String command, AsciiArtAlgorithm algorithm) {
        switch (command) {
            case EXIT_COMMAND -> {
                return false;
            }
            case RES_UP -> handleResUp();
            case RES_DOWN -> handleResDown();
            case OUTPUT_CONSOLE_COMMAND -> output = CONSOLE_ASCII_OUTPUT;
            case OUTPUT_HTML_COMMAND -> output = HTML_ASCII_OUTPUT;
            case ASCII_ART_COMMAND -> runAlgorithm(algorithm);
            case CHARS -> printAllCharsSorted();
            default -> handleComplexCommand(command);
        }
        return true;
    }

    /**
     * Handles the "res down" command, decreasing the resolution if possible.
     */
    private void handleResDown() {
        if (!alogithmparameters.resDown()) {
            System.out.println(RES_EXCEED_WIDTH);
        } else {
            System.out.println(RES_UPDATE_MESSAGE + alogithmparameters.getResolution()+DOT);
        }
    }

    /**
     * Handles the "res up" command, increasing the resolution if possible.
     */
    private void handleResUp() {
        if (!alogithmparameters.resUp()) {
            System.out.println(RES_EXCEED_WIDTH);
        } else {
            System.out.println(RES_UPDATE_MESSAGE + alogithmparameters.getResolution()+DOT);
        }
    }

    /**
     * Prints all characters in the charset sorted.
     */
    private void printAllCharsSorted() {
        List<Character> sortedList = alogithmparameters.getCharMatcher().GetAllCharsSorted();

        for (int i = 0; i < sortedList.size(); i++) {
            System.out.print(sortedList.get(i));
            if (i != sortedList.size() + LAST_INDEX) {
                System.out.print(SPACE_CHAR);
            }
        }
        System.out.println();
    }

    /**
     * Runs a complex command, handling special cases like adding or removing characters from the charset.
     *
     * @param command The command to run.
     * @throws AddException             If an error occurs while adding characters.
     * @throws RemoveException          If an error occurs while removing characters.
     * @throws OutputException          If an error occurs while changing the output mode.
     * @throws ResException             If an error occurs while changing the resolution.
     * @throws InvalidCommandException If the command is invalid.
     */
    private void runComplexCommand(String command) throws AddException, RemoveException, OutputException,
            ResException, InvalidCommandException {
        if (command.startsWith(ADD_PREFIX + SPACE_PREFIX)) {
            handleAddCommand(command);
        } else if (command.equals(ADD_PREFIX)) {
            throw new AddException(INVALID_ADD_REQUEST);
        } else if (command.startsWith(REMOVE_PREFIX + SPACE_PREFIX)) {
            handleRemoveCommand(command);
        } else if (command.equals(REMOVE_PREFIX)) {
            throw new RemoveException(INVALID_REMOVE_REQUEST);
        } else if (command.startsWith(OUTPUT_PREFIX + SPACE_PREFIX) || command.equals(OUTPUT_PREFIX)) {
            throw new OutputException(INVALID_OUTPUT_REQUEST);
        } else if (command.startsWith(RES_PREFIX + SPACE_PREFIX) || command.equals(RES_PREFIX)) {
            throw new ResException(INVALID_RES_REQUEST);
        } else if (command.startsWith(CHANGE_IMAGE_PREFIX + SPACE_PREFIX)) {
            handleImageUpdate(command);
        } else {
            throw new InvalidCommandException(INVALID_COMMAND);
        }
    }

    /**
     * Handles a complex command, catching and printing any exceptions that may occur during execution.
     *
     * @param command The command to handle.
     */
    private void handleComplexCommand(String command) {
        try {
            runComplexCommand(command);
        }
        catch (AddException | RemoveException | OutputException | ResException |
               InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles updating the image used for ASCII art generation based on the provided command. If the image
     * path is valid, it updates the current image; otherwise, it prints an error message.
     *
     * @param command The command containing the new image path.
     */
    private void handleImageUpdate(String command) {
        try {
            alogithmparameters.updateImage(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } catch (IOException e) {
            System.out.println(IO_EXPECTION);
        }
    }

    /**
     * Handles the execution of an "add" command, which adds characters to the charset used for ASCII
     * art generation.
     *
     * @param command The command specifying the characters to add.
     * @throws AddException If an error occurs while executing the command.
     */
    private void handleAddCommand(String command) throws AddException{
        try {
            runAddingCommand(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } catch (AddException e) {
            throw new AddException(INVALID_ADD_REQUEST);
        }
    }

    /**
     * Handles the execution of a "remove" command, which removes characters from the charset used for
     * ASCII art generation.
     *
     * @param command The command specifying the characters to remove.
     * @throws RemoveException If an error occurs while executing the command.
     */
    private void handleRemoveCommand(String command) throws RemoveException{
        try {
            runRemovingCommand(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } catch (RemoveException e) {
            throw new RemoveException(INVALID_REMOVE_REQUEST);
        }
    }

    /**
     * Runs a command to add characters to the charset used for ASCII art generation. If the command
     * specifies a single character, it adds that character to the charset. If the command is "all", it adds
     * all printable ASCII characters. If the command is "space", it adds the space character. If the command
     * specifies a range of characters (e.g., "a-z"), it adds all characters in that range. If the command
     * format is invalid, it prints an error message.
     *
     * @param command The command specifying the characters to add.
     */
    private void runAddingCommand(String command) throws AddException{
        if (command.length() == SINGLE_CHAR) {
            alogithmparameters.getCharMatcher().addChar(command.charAt(FIRST_INDEX));
        } else if (command.equals(ALL_CHARS)) {
            for (char c = SPACE_CHAR; c <= TILDA_CHAR; c++) {
                alogithmparameters.getCharMatcher().addChar(c);
            }
        } else if (command.equals(SPACE)) {
            alogithmparameters.getCharMatcher().addChar(SPACE_CHAR);
        } else {
            String[] sep = command.split(MINUS_REGEX);
            if (sep.length != TWO_CHARS || sep[FIRST_INDEX].length() != SINGLE_CHAR ||
                    sep[SECOND_INDEX].length() != SINGLE_CHAR) {
//                System.out.println(INVALID_ADD_REQUEST);
                throw new AddException(INVALID_ADD_REQUEST);
            } else {
                addCharsInRange(sep[FIRST_INDEX].charAt(FIRST_INDEX),
                        sep[SECOND_INDEX].charAt(FIRST_INDEX));
            }
        }
    }

    /**
     * Adds characters in the specified range (inclusive) to the charset used for ASCII art generation.
     *
     * @param c  The starting character of the range.
     * @param c1 The ending character of the range.
     */
    private void addCharsInRange(char c, char c1) {
        char min_c = ((int) c < (int) c1) ? c : c1;
        char max_c = ((int) c > (int) c1) ? c : c1;
        for (char ch = min_c; ch <= max_c; ch++) {
            alogithmparameters.getCharMatcher().addChar(ch);
        }
    }

    /**
     * Runs a command to remove characters from the charset used for ASCII art generation. If the command
     * specifies a single character, it removes that character from the charset. If the command is "all", it
     * removes all printable ASCII characters. If the command is "space", it removes the space character. If
     * the command specifies a range of characters (e.g., "a-z"), it removes all characters in that range. If
     * the command format is invalid, it prints an error message.
     *
     * @param command The command specifying the characters to remove.
     */
    private void runRemovingCommand(String command) throws RemoveException{
        if (command.length() == SINGLE_CHAR) {
            alogithmparameters.getCharMatcher().removeChar(command.charAt(FIRST_INDEX));
        } else if (command.equals(ALL_CHARS)) {
            for (char c = SPACE_CHAR; c <= TILDA_CHAR; c++) {
                alogithmparameters.getCharMatcher().removeChar(c);
            }
        } else if (command.equals(SPACE)) {
            alogithmparameters.getCharMatcher().removeChar(SPACE_CHAR);
        } else {
            String[] sep = command.split(MINUS_REGEX);
            if (sep.length != TWO_CHARS || sep[FIRST_INDEX].length() != SINGLE_CHAR ||
                    sep[SECOND_INDEX].length() != SINGLE_CHAR) {
                throw new RemoveException(INVALID_REMOVE_REQUEST);
            } else {
                removeCharsInRange(sep[FIRST_INDEX].charAt(FIRST_INDEX),
                        sep[SECOND_INDEX].charAt(FIRST_INDEX));
            }
        }
    }

    /**
     * Removes characters in the specified range (inclusive) from the charset used for ASCII art generation.
     *
     * @param c  The starting character of the range.
     * @param c1 The ending character of the range.
     */
    private void removeCharsInRange(char c, char c1) {
        char min_c = ((int) c < (int) c1) ? c : c1;
        char max_c = ((int) c > (int) c1) ? c : c1;
        for (char ch = min_c; ch <= max_c; ch++) {
            alogithmparameters.getCharMatcher().removeChar(ch);
        }
    }

    /**
     * Runs the ASCII art algorithm and outputs the result.
     *
     * @param algorithm The ASCII art algorithm to run.
     */
    private void runAlgorithm(AsciiArtAlgorithm algorithm) {
        if (alogithmparameters.getCharMatcher().GetAllCharsSorted().isEmpty()) {
            System.out.println(EMPTY_CHARSET_MESSAGE);
            return;
        }
        char[][] resultChars = algorithm.run();
        output.out(resultChars);
    }

    /**
     * The main method that starts the shell.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Shell().run();
    }
}


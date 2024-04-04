package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

import java.io.IOException;
import java.util.List;

import static ascii_art.ShellConstant.*;


/**
 * The Shell class represents the command-line interface for interacting with the AsciiArtAlgorithm. It allows users to
 * input various commands to manipulate the ASCII art generation process, change settings, and view the output. The
 * class handles user input and delegates the corresponding actions to the AsciiArtAlgorithm and associated
 * components.
 */
public class Shell {

    //region STATIC MEMBERS
    /**
     * Console output handler for ASCII art.
     */
    private static final ConsoleAsciiOutput CONSOLE_ASCII_OUTPUT = new ConsoleAsciiOutput();

    /**
     * HTML output handler for ASCII art.
     */
    private static final HtmlAsciiOutput HTML_ASCII_OUTPUT = new HtmlAsciiOutput(OUTPUT_HTML_PATH, FONT);
    //endregion
    //region PRIVATE MEMBERS
    /**
     * Current output mode for ASCII art rendering.
     */
    private AsciiOutput output = CONSOLE_ASCII_OUTPUT;

    /**
     * Parameters and settings for the ASCII art algorithm.
     */
    private AlogithmParameters alogithmparameters;
    //endregion


    //region API

    /**
     * Constructs a new instance of the Shell class.
     */
    public Shell() {
    }

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
    //endregion

    //region PRIVATE METHODS

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
            System.out.println(RES_UPDATE_MESSAGE + alogithmparameters.getResolution() + DOT);
        }
    }

    /**
     * Handles the "res up" command, increasing the resolution if possible.
     */
    private void handleResUp() {
        if (!alogithmparameters.resUp()) {
            System.out.println(RES_EXCEED_WIDTH);
        } else {
            System.out.println(RES_UPDATE_MESSAGE + alogithmparameters.getResolution() + DOT);
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
     * @throws AddException            If an error occurs while adding characters.
     * @throws RemoveException         If an error occurs while removing characters.
     * @throws OutputException         If an error occurs while changing the output mode.
     * @throws ResException            If an error occurs while changing the resolution.
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
        } catch (AddException | RemoveException | OutputException | ResException |
                 InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles updating the image used for ASCII art generation based on the provided command. If the image path is
     * valid, it updates the current image; otherwise, it prints an error message.
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
     * Handles the execution of an "add" command, which adds characters to the charset used for ASCII art generation.
     *
     * @param command The command specifying the characters to add.
     * @throws AddException If an error occurs while executing the command.
     */
    private void handleAddCommand(String command) throws AddException {
        try {
            runAddingCommand(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } catch (AddException e) {
            throw new AddException(INVALID_ADD_REQUEST);
        }
    }

    /**
     * Handles the execution of a "remove" command, which removes characters from the charset used for ASCII art
     * generation.
     *
     * @param command The command specifying the characters to remove.
     * @throws RemoveException If an error occurs while executing the command.
     */
    private void handleRemoveCommand(String command) throws RemoveException {
        try {
            runRemovingCommand(command.split(SPACE_REGEX)[ATTER_REGEX]);
        } catch (RemoveException e) {
            throw new RemoveException(INVALID_REMOVE_REQUEST);
        }
    }

    /**
     * Runs a command to add characters to the charset used for ASCII art generation. If the command specifies a single
     * character, it adds that character to the charset. If the command is "all", it adds all printable ASCII
     * characters. If the command is "space", it adds the space character. If the command specifies a range of
     * characters (e.g., "a-z"), it adds all characters in that range. If the command format is invalid, it prints an
     * error message.
     *
     * @param command The command specifying the characters to add.
     */
    private void runAddingCommand(String command) throws AddException {
        if (command.length() == SINGLE_CHAR) {
            // Add a single character
            alogithmparameters.getCharMatcher().addChar(command.charAt(FIRST_INDEX));
        } else if (command.equals(ALL_CHARS)) {
            for (char c = SPACE_CHAR; c <= TILDA_CHAR; c++) {
                // Add all printable ASCII characters
                alogithmparameters.getCharMatcher().addChar(c);
            }
        } else if (command.equals(SPACE)) {
            // Add space character
            alogithmparameters.getCharMatcher().addChar(SPACE_CHAR);
        } else {
            String[] sep = command.split(MINUS_REGEX);
            // Add characters in the specified range
            if (sep.length != TWO_CHARS || sep[FIRST_INDEX].length() != SINGLE_CHAR ||
                    // Check if the command format is valid
                    sep[SECOND_INDEX].length() != SINGLE_CHAR) {
                throw new AddException(INVALID_ADD_REQUEST);
            } else {
                // Add characters in the specified range
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
     * Runs a command to remove characters from the charset used for ASCII art generation. If the command specifies a
     * single character, it removes that character from the charset. If the command is "all", it removes all printable
     * ASCII characters. If the command is "space", it removes the space character. If the command specifies a range of
     * characters (e.g., "a-z"), it removes all characters in that range. If the command format is invalid, it prints
     * an error message.
     *
     * @param command The command specifying the characters to remove.
     */
    private void runRemovingCommand(String command) throws RemoveException {
        if (command.length() == SINGLE_CHAR) {
            // Remove a single character
            alogithmparameters.getCharMatcher().removeChar(command.charAt(FIRST_INDEX));
        } else if (command.equals(ALL_CHARS)) {
            for (char c = SPACE_CHAR; c <= TILDA_CHAR; c++) {
                // Remove all printable ASCII characters
                alogithmparameters.getCharMatcher().removeChar(c);
            }
        } else if (command.equals(SPACE)) {
            // Remove space character
            alogithmparameters.getCharMatcher().removeChar(SPACE_CHAR);
        } else {
            // Remove characters in the specified range
            String[] sep = command.split(MINUS_REGEX);
            if (sep.length != TWO_CHARS || sep[FIRST_INDEX].length() != SINGLE_CHAR ||
                    sep[SECOND_INDEX].length() != SINGLE_CHAR) {
                // Check if the command format is valid
                throw new RemoveException(INVALID_REMOVE_REQUEST);
            } else {
                // Remove characters in the specified range
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

    //endregion


    /**
     * The main method that starts the shell.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Shell().run();
    }

}


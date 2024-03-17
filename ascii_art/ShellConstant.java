package ascii_art;

class ShellConstant {
    /**
     * Command string for changing the output mode to console.
     */
     static final String OUTPUT_CONSOLE_COMMAND = "output console";

    /**
     * Command string for changing the output mode to HTML.
     */
     static final String OUTPUT_HTML_COMMAND = "output html";

    /**
     * Default path for the HTML output file.
     */
     static final String OUTPUT_HTML_PATH = "out.html";

    /**
     * Default font used for ASCII art rendering.
     */
     static final String FONT = "Courier New";

    /**
     * Command string prefix for user input.
     */
     static final String PREFIX = ">>> ";

    /**
     * Command string for generating ASCII art.
     */
     static final String ASCII_ART_COMMAND = "asciiArt";

    /**
     * Command string for exiting the shell.
     */
     static final String EXIT_COMMAND = "exit";

    /**
     * Prefix for commands related to changing the image.
     */
     static final String CHANGE_IMAGE_PREFIX = "image";

    /**
     * Prefix for commands related to changing the output mode.
     */
     static final String OUTPUT_PREFIX = "output";

    /**
     * Prefix for commands related to changing the resolution.
     */
     static final String RES_PREFIX = "res";

    /**
     * Prefix for commands related to adding characters to the charset.
     */
     static final String ADD_PREFIX = "add";

    /**
     * Prefix for commands related to removing characters from the charset.
     */
     static final String REMOVE_PREFIX = "remove";

    /**
     * Prefix for commands requiring space separation.
     */
     static final String SPACE_PREFIX = " ";

    /**
     * Command string for increasing resolution.
     */
     static final String RES_UP = "res up";

    /**
     * Command string for decreasing resolution.
     */
     static final String RES_DOWN = "res down";

    /**
     * Length of a single character.
     */
     static final int SINGLE_CHAR = 1;

    /**
     * Index of the first character in an array or string.
     */
     static final int FIRST_INDEX = 0;

    /**
     * Index of the last character in an array or string.
     */
     static final int LAST_INDEX = -1;

    /**
     * Index of the second character in an array or string.
     */
     static final int SECOND_INDEX = 1;

    /**
     * Regex group index used for splitting commands.
     */
     static final int ATTER_REGEX = 1;

    /**
     * Number of characters in a range specifier.
     */
     static final int TWO_CHARS = 2;

    /**
     * Character representing a space.
     */
     static final char SPACE_CHAR = ' ';

    /**
     * Character representing a tilde (~).
     */
     static final char TILDA_CHAR = '~';

    /***
     * character representing a dot(.) to print in the console
     * after the resolution is changed.
     * */
     static final char DOT = '.';

    /**
     * Command string representing all printable ASCII characters.
     */
     static final String ALL_CHARS = "all";

    /**
     * Command string representing the space character.
     */
     static final String SPACE = "space";

    /**
     * Command string for listing all characters in the charset.
     */
     static final String CHARS = "chars";

    /**
     * Regular expression for splitting commands with spaces.
     */
     static final String SPACE_REGEX = " ";

    /**
     * Regular expression for splitting range specifiers.
     */
     static final String MINUS_REGEX = "-";

    /**
     * Error message for an invalid request to add characters.
     */
     static final String INVALID_ADD_REQUEST = "Did not add due to incorrect format.";

    /**
     * Error message for an invalid request to remove characters.
     */
     static final String INVALID_REMOVE_REQUEST = "Did not remove due to incorrect format.";

    /**
     * Error message for an invalid request to change the output mode.
     */
     static final String INVALID_OUTPUT_REQUEST = "Did not change output method due to incorrect " +
            "format.";

    /**
     * Error message for an invalid request to change the resolution.
     */
     static final String INVALID_RES_REQUEST = "Did not change resolution due to incorrect format.";

    /**
     * Error message for attempting to change the resolution beyond the image boundaries.
     */
     static final String RES_EXCEED_WIDTH = "Did not change resolution due to exceeding boundaries.";

    /**
     * Message indicating a successful resolution change.
     */
     static final String RES_UPDATE_MESSAGE = "Resolution set to ";

    /**
     * Error message for an I/O exception while handling an image file.
     */
     static final String IO_EXPECTION = "Did not execute due to problem with image file.";

    /**
     * Error message for an invalid command.
     */
     static final String INVALID_COMMAND = "Did not execute due to incorrect command.";

    /**
     * Error message for attempting to execute a command when the charset is empty.
     */
     static final String EMPTY_CHARSET_MESSAGE = "Did not execute. Charset is empty.";
}

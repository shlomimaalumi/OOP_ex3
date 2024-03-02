package ascii_art;

/**
 * Exception class for errors related to adding characters to the charset in ASCII art processing. This
 * exception is thrown when there is an issue with adding characters to the character set used in ASCII art.
 */
class AddException extends Exception {

    /**
     * Constructs an AddException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public AddException(String message) {

        super(message);
    }
}

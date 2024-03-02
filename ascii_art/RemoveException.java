package ascii_art;

/**
 * Exception class for errors related to removing characters from the charset in ASCII art processing. This
 * exception is thrown when there is an issue with removing characters from the character set used in ASCII
 * art.
 */
class RemoveException extends Exception {

    /**
     * Constructs a RemoveException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public RemoveException(String message) {
        super(message);
    }
}

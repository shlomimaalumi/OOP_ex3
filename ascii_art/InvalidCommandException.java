package ascii_art;

/**
 * Exception class for invalid commands.
 */
class InvalidCommandException extends Exception {
    /**
     * Constructs an InvalidCommandException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}

package ascii_art;

/**
 * Exception class for errors related to output methods in ASCII art processing. This exception is thrown
 * when there is an issue with generating or displaying the ASCII art output.
 */
class OutputException extends Exception {

    /**
     * Constructs an OutputException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public OutputException(String message) {

        super(message);
    }
}

package ascii_art;

/**
 * Exception class for errors related to resolution changes in ASCII art processing. This exception is thrown
 * when there is an issue with adjusting the resolution or dimensions of the ASCII art.
 */
class ResException extends Exception {

    /**
     * Constructs a ResException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ResException(String message) {
        super(message);
    }
}

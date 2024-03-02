package ascii_art;

/**
 * Exception class for errors related to adding characters to the charset.
 */
class addException extends Exception {
    public addException(String message) {
        super(message);
    }
}

/**
 * Exception class for errors related to removing characters from the charset.
 */
class removeException extends Exception {
    public removeException(String message) {
        super(message);
    }
}

/**
 * Exception class for errors related to output methods.
 */
class outputException extends Exception {
    public outputException(String message) {
        super(message);
    }
}

/**
 * Exception class for errors related to resolution changes.
 */
class resException extends Exception {
    public resException(String message) {
        super(message);
    }
}

/**
 * Exception class for invalid commands.
 */
class invalidCommandException extends Exception {
    public invalidCommandException(String message) {
        super(message);
    }
}

package ascii_art;

import java.util.Scanner;

public class KeyboardInput {
    //region CLASS VARIABLES
    private static KeyboardInput keyboardInputObject = null;
    private Scanner scanner;

    //endregion


    //region CONSTRUCTOR(singelton)

    private KeyboardInput() {
        this.scanner = new Scanner(System.in);
    }

    //endregion


    //region API

    public static KeyboardInput getObject() {
        if (KeyboardInput.keyboardInputObject == null) {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }

    public static void Refresh() {
        KeyboardInput.getObject().scanner.close();
        KeyboardInput.getObject().scanner = new Scanner(System.in);
    }

    public static String readLine() {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }

    //endregion
}

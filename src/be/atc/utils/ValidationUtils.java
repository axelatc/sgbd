package be.atc.utils;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {
    private static final Logger log = Logger.getLogger(ValidationUtils.class);

    private ValidationUtils() {
    }


    public static List<String> getErrorMessagesOrEmptyForTextualInput(String input, String inputLabel, int minLength, int maxLength) {
        List<String> errorMessages = new ArrayList<String>();
        if (!hasContent(input)) {
            errorMessages.add(inputLabel + " n'a pas de contenu");
        } else {
            if (input.length() < minLength
                    || input.length() > maxLength) {
                String message = inputLabel + " doit être entre " + minLength + " et " + maxLength + " charactères.";
                errorMessages.add(message);
            }
        }
        return errorMessages;
    }

    /**
     * WARNING: This method is extremely common, and should be in a utility class.
     * (It really should be in the JDK, as a static method of the String class.)
     */
    public static boolean hasContent(String string) {
        log.debug("Vérification qu'une chaîne de chars a du contenu: " + string);
        return (string != null && string.trim().length() > 0);
    }

    /**
     * Returns true only if the field passes the test, and is NOT null.
     */
    private boolean ensureNotNull(Object field, String errorMsg, List<String> errors) {
        boolean result = true;
        if (field == null) {
            errors.add(errorMsg);
            result = false;
        }
        return result;
    }
}

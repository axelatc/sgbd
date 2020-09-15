package be.atc.models;

import java.util.ArrayList;
import java.util.List;

import static be.atc.utils.ValidationUtils.getErrorMessagesOrEmptyForTextualInput;

public class UserModel {
    private final int USERNAME_MIN_LENGTH = 5;
    private final int USERNAME_MAX_LENGTH = 255;
    private final int PASSWORD_MIN_LENGTH = 8;
    private final int PASSWORD_MAX_LENGTH = 255;
    private final String username;
    private final String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static String generateRandomPassword() {
        return "randompassword";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public class UserValidator extends Validator {

        @Override
        public boolean isValid() {
            return usernameIsValid() && passwordIsValid();
        }

        public boolean usernameIsValid() {
            return getErrorMessagesOrEmptyForUsername().isEmpty();
        }

        public boolean passwordIsValid() {
            return getErrorMessagesOrEmptyForPassword().isEmpty();
        }

        @Override
        protected List<String> generateAllErrorMessagesOrEmpty() {
            ArrayList<String> errorMessages = new ArrayList<String>();
            errorMessages.addAll(getErrorMessagesOrEmptyForUsername());
            errorMessages.addAll(getErrorMessagesOrEmptyForPassword());
            return errorMessages;
        }

        public final List<String> getErrorMessagesOrEmptyForUsername() {
            return getErrorMessagesOrEmptyForTextualInput(username, "username", USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);
        }

        public final List<String> getErrorMessagesOrEmptyForPassword() {
            return getErrorMessagesOrEmptyForTextualInput(password, "password", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        }
    }
}

package be.atc.models;

import java.util.regex.Pattern;

public class UserModel {
    private static final int MIN_LENGTH_LOGIN = 1;
    private static final int MAX_LENGTH_LOGIN = 255;
    private static final int MIN_LENGTH_PASSWORD = 8;
    private static final int MAX_LENGTH_PASSWORD = 255;
    private static final String LOGIN_VALIDITY_PATTERN = "[A-Za-z0-9_]+";
    private static final String PASSWORD_VALIDITY_PATTERN = "[A-Za-z0-9_]+";

    private static boolean loginIsValid(String login) {
        int loginLength = login.length();
        return (login != null
                && !login.isEmpty()
                && loginLength >= MIN_LENGTH_LOGIN
                && loginLength <= MAX_LENGTH_LOGIN
                && login.matches(LOGIN_VALIDITY_PATTERN));
    }

    private static boolean passwordIsValid(String password) {
        int passwordLength = password.length();
        return (password != null
                && !password.isEmpty()
                && passwordLength >= MIN_LENGTH_PASSWORD
                && passwordLength <= MAX_LENGTH_PASSWORD
                && password.matches(PASSWORD_VALIDITY_PATTERN));
    }


    private String login;
    private String password;

    public UserModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }
    private void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    private void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return loginIsValid(this.login) && passwordIsValid(this.password);
    }


}

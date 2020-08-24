package be.atc.models;

import java.util.List;

public interface Validator {
    boolean isValid();

    List<String> getAllErrorMessagesOrEmpty();
}

package be.atc.models;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidatorImpl implements Validator {
    protected List<String> allErrorMessages;

    public ValidatorImpl() {
        allErrorMessages = generateAllErrorMessagesOrEmpty();
    }

    @Override
    public boolean isValid() {
        return allErrorMessages.isEmpty();
    }

    protected abstract List<String> generateAllErrorMessagesOrEmpty();

    @Override
    public List<String> getAllErrorMessagesOrEmpty() {
        return new ArrayList<>(this.allErrorMessages);
    }
}

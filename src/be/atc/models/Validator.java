package be.atc.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator {
    protected List<String> allErrorMessages;

    public Validator() {
    }

    public boolean isValid() { return getAllErrorMessagesOrEmpty().isEmpty();
    }

    protected abstract List<String> generateAllErrorMessagesOrEmpty();

    public List<String> getAllErrorMessagesOrEmpty() {
        if (allErrorMessages == null) { allErrorMessages = generateAllErrorMessagesOrEmpty(); };
        return new ArrayList<>(this.allErrorMessages);
    }
}

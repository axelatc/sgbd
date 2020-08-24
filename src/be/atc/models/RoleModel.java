package be.atc.models;

import be.atc.dataAccess.entities.AuthorityEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static be.atc.utils.ValidationUtils.getErrorMessagesOrEmptyForTextualInput;

public class RoleModel {
    private final int LABEL_MIN_LENGTH = 5;
    private final int LABEL_MAX_LENGTH = 255;
    private final int DESCRIPTION_MIN_LENGTH = 1;
    private final int DESCRIPTION_MAX_LENGTH = 2000;
    private final String label;
    private final String description;
    private final Collection<AuthorityEntity> authorities;

    public RoleModel(String label, String description, Collection<AuthorityEntity> authorities) {
        this.label = label;
        this.description = description;
        this.authorities = authorities;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public class RoleValidator extends ValidatorImpl {

        @Override
        protected List<String> generateAllErrorMessagesOrEmpty() {
            ArrayList<String> allErrorMessages = new ArrayList<String>();
            allErrorMessages.addAll(getErrorMessagesOrEmptyForLabel());
            allErrorMessages.addAll(getErrorMessagesOrEmptyForDescription());
            return allErrorMessages;
        }

        public List<String> getErrorMessagesOrEmptyForLabel() {
            return getErrorMessagesOrEmptyForTextualInput(label, "label",
                    LABEL_MIN_LENGTH, LABEL_MAX_LENGTH);
        }

        public boolean descriptionIsValid() {
            return !getErrorMessagesOrEmptyForDescription().isEmpty();
        }

        public List<String> getErrorMessagesOrEmptyForDescription() {
            return getErrorMessagesOrEmptyForTextualInput(description, "description",
                    DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);
        }
    }
}

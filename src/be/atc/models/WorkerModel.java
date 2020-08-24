package be.atc.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static be.atc.utils.ValidationUtils.getErrorMessagesOrEmptyForTextualInput;
import static be.atc.utils.ValidationUtils.hasContent;

public class WorkerModel {
    private final String BIRTHDATE_PATTERN = "yyyy-MM-dd";
    private final DateTimeFormatter BIRTHDATE_FORMATTER = new DateTimeFormatterBuilder().appendPattern(BIRTHDATE_PATTERN).parseStrict().toFormatter();

    private final String birthdate;
    private final String firstName;
    private final String lastName;

    public WorkerModel(String firstName, String lastName, String birthdate) {
        this.birthdate = birthdate;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdateAsLocalDate() {
        return LocalDate.parse(birthdate, BIRTHDATE_FORMATTER);
    }


    public class WorkerValidator extends ValidatorImpl {
        private final int FIRSTNAME_MIN_LENGTH = 1;
        private final int FIRSTNAME_MAX_LENGTH = 255;
        private final int LASTNAME_MIN_LENGTH = 1;
        private final int LASTNAME_MAX_LENGTH = 255;

        @Override
        protected List<String> generateAllErrorMessagesOrEmpty() {
            ArrayList<String> allErrorMessages = new ArrayList<String>();

            List<String> firstNameErrorMessages = getErrorMessagesOrEmptyForTextualInput(firstName, "firstName", FIRSTNAME_MIN_LENGTH, FIRSTNAME_MAX_LENGTH);
            allErrorMessages.addAll(firstNameErrorMessages);

            List<String> lastNameErrorMessages = getErrorMessagesOrEmptyForTextualInput(lastName, "lastName", LASTNAME_MIN_LENGTH, LASTNAME_MAX_LENGTH);
            allErrorMessages.addAll(lastNameErrorMessages);

            allErrorMessages.addAll(getErrorMessagesOrEmptyForBirthdate());
            return allErrorMessages;
        }

        private List<String> getErrorMessagesOrEmptyForBirthdate() {
            List<String> errorMessages = new ArrayList<String>();
            if (!hasContent(birthdate)) {
                errorMessages.add("birthdate n'a pas de contenu");
            } else {
                try {
                    LocalDate.parse(birthdate, BIRTHDATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    errorMessages.add("birthdate doit être au format de date " + BIRTHDATE_PATTERN);
                }
            }
            return errorMessages;
        }
    }

}


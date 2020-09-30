package be.atc.models;

import be.atc.dataAccess.entities.SexeType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static be.atc.utils.ValidationUtils.hasContent;


public class SexeTypeValidator extends Validator {
    private static final Logger log = Logger.getLogger(SexeTypeValidator.class);
    private final String value;

    public SexeTypeValidator(String value) {
        this.value = value;
        super.allErrorMessages = generateAllErrorMessagesOrEmpty();
    }


    @Override
    protected List<String> generateAllErrorMessagesOrEmpty() {
        List<String> errorMessages = new ArrayList<>();
        log.debug("sexe type inputted: " + value);

        if (hasContent(value)) {
            try {
                SexeType sexeType = SexeType.valueOf(value);
            } catch (IllegalArgumentException e) {
                errorMessages.add("Le sexe choisi " + value +
                        "n'est pas valide pour convertir en un enum SexeType");
                log.debug("Le sexe choisi " + value +
                        "n'est pas valide pour convertir en un enum SexeType", e);
            }
        } else {
            errorMessages.add("La chaîne sexe rentrée n'a pas de contenu");
        }
        return errorMessages;
    }
}

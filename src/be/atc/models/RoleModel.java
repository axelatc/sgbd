package be.atc.models;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.AuthorityEntity;
import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.services.AuthorityService;
import be.atc.dataAccess.services.RoleService;
import be.atc.utils.ValidationUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static be.atc.utils.ValidationUtils.getErrorMessagesOrEmptyForTextualInput;

public class RoleModel extends Validator {
    private static final Logger log = Logger.getLogger(RoleModel.class);
    private final int LABEL_MIN_LENGTH = 5;
    private final int LABEL_MAX_LENGTH = 255;
    private final int DESCRIPTION_MIN_LENGTH = 1;
    private final int DESCRIPTION_MAX_LENGTH = 2000;
    private final String label;
    private final String description;
    private final String[] authoritiesIds;

    public RoleModel(String label, String description, String[] authoritiesIds) {
        log.debug("authorities ids: " + Arrays.toString(authoritiesIds));
        this.label = label;
        this.description = description;
        this.authoritiesIds = authoritiesIds;
    }

    public String getLabel() { return label; }
    public String getDescription() { return description; }
    public String[] getAuthoritiesIds() {
        return authoritiesIds;
    }

    @Override
    protected List<String> generateAllErrorMessagesOrEmpty() {
        ArrayList<String> allErrorMessages = new ArrayList<String>();
        allErrorMessages.addAll(getErrorMessagesOrEmptyForLabel());
        allErrorMessages.addAll(getErrorMessagesOrEmptyForDescription());
        allErrorMessages.addAll(getErrorMessagesOrEmptyForAuthoritiesIds());
        return allErrorMessages;
    }


    private List<String> getErrorMessagesOrEmptyForLabel() {
        return getErrorMessagesOrEmptyForTextualInput(label, "label",
                LABEL_MIN_LENGTH, LABEL_MAX_LENGTH);
    }

    private List<String> getErrorMessagesOrEmptyForDescription() {
        return getErrorMessagesOrEmptyForTextualInput(description, "description",
                DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);
    }

    private List<String> getErrorMessagesOrEmptyForAuthoritiesIds() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        AuthorityService service = new AuthorityService(EMF.getEM());
        if(authoritiesIds.length == 0) {
            errorMessages.add("Veuillez sélectionner au moins une authority.");
            return errorMessages;
        }
        for (String id:
             authoritiesIds) {
            if(!ValidationUtils.isEntityId(id)) {
                errorMessages.add("Veuillez réessayer.");
                return errorMessages;
            }
            else {
                int idAsInt = Integer.parseInt(id);
                if(service.findByIdOrNull(idAsInt) == null) {
                    errorMessages.add("Veuillez réessayer.");
                    return errorMessages;
                }
            }
        };
        return errorMessages;
    }

    public RoleEntity getEntity_OrNullIfValidationFailed(RoleService roleService) {
        if(isValid()) {
            AuthorityService authorityService = new AuthorityService(EMF.getEM());
            Collection<AuthorityEntity> authorities = new ArrayList<>();
            for (String id:
                    authoritiesIds) {
                int idAsInt = Integer.parseInt(id);
                authorities.add(authorityService.findByIdOrNull(idAsInt));
            }

            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setLabel(label);
            roleEntity.setDescription(description);
            roleEntity.setAuthorities(authorities);
            return roleEntity;

        } else {
            return null;
        }
    }


}

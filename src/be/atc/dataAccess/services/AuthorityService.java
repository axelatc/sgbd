package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.AuthorityEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collection;

public class AuthorityService extends Service<AuthorityEntity> {

    public AuthorityService(EntityManager em) {
        super(em);
    }

    @Override
    public boolean hasDuplicate(AuthorityEntity w) {
        return (findAuthorityByLabelOrNull(w.getLabel()) != null);
    }

    @Override
    public AuthorityEntity findByIdOrNull(int id) {
        return em.find(AuthorityEntity.class, id);
    }

    @Override
    public Collection<AuthorityEntity> findAllOrNull() {
        try {
            return em.createNamedQuery("Authorities.findAll", AuthorityEntity.class)
                    .getResultList();
        } catch (NoResultException e) {
            log.debug("The query found no authority to return", e);
            return null;
        }
    }

    public AuthorityEntity findAuthorityByLabelOrNull(String label) throws IllegalArgumentException {
        log.debug("Finding authority of label " + label);

        if (label != null && !label.isEmpty()) {
            try {
                return em.createNamedQuery("Authorities.findAuthorityByLabel", AuthorityEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                log.debug("The query found no authority to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't search authority in database: the label is null or empty");
        }
    }
}


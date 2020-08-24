package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.AuthorityEntity;
import be.atc.dataAccess.entities.RoleEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collection;

public class RoleService extends ServiceImpl<RoleEntity> {

    public RoleService(EntityManager em) {
        super(em);
    }

    public RoleEntity create(String label,
                             String description,
                             Collection<AuthorityEntity> authorities) {
        RoleEntity newRole = new RoleEntity();
        newRole.setLabel(label);
        newRole.setDescription(description);
        newRole.setAuthorities(authorities);
        return newRole;
    }


    @Override
    public boolean hasDuplicate(RoleEntity w) {
        return (findRoleByLabelOrNull(w.getLabel()) != null);
    }

    @Override
    public RoleEntity findByIdOrNull(int id) {
        return em.find(RoleEntity.class, id);
    }

    @Override
    public Collection<RoleEntity> findAllOrNull() {
        try {
            return em.createNamedQuery("Roles.findAll", RoleEntity.class)
                    .getResultList();
        } catch (NoResultException e) {
            log.debug("The query found no role to return", e);
            return null;
        }
    }

    public RoleEntity findRoleByLabelOrNull(String label) throws IllegalArgumentException {
        log.debug("Finding role of label " + label);

        if (label != null && !label.isEmpty()) {
            try {
                return em.createNamedQuery("Roles.findRoleByLabel", RoleEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                log.debug("The query found no role to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't search role in database: the label is null or empty");
        }
    }
}

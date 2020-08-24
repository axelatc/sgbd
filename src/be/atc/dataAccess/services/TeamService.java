package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.TeamEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collection;

public class TeamService extends ServiceImpl<TeamEntity> {
    public TeamService(EntityManager em) {
        super(em);
    }

    public TeamEntity create(String label) {
        TeamEntity newTeam = new TeamEntity();
        newTeam.setLabel(label);
        return newTeam;
    }

    @Override
    public boolean hasDuplicate(TeamEntity t) {
        return (findTeamByLabelOrNull(t.getLabel()) != null);
    }

    @Override
    public TeamEntity findByIdOrNull(int id) {
        return em.find(TeamEntity.class, id);
    }

    @Override
    public Collection<TeamEntity> findAllOrNull() {
        try {
            return em.createNamedQuery("Teams.findAll", TeamEntity.class)
                    .getResultList();
        } catch (NoResultException e) {
            log.debug("The query found no team to return", e);
            return null;
        }
    }

    public TeamEntity findTeamByLabelOrNull(String label) throws IllegalArgumentException {
        log.debug("Finding team of label " + label);

        if (label != null && !label.isEmpty()) {
            try {
                return em.createNamedQuery("Teams.findTeamByLabel", TeamEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                log.debug("The query found no team to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't search team in database: the label is null or empty");
        }
    }
}

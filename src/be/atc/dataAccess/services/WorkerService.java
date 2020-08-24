package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.entities.SexeType;
import be.atc.dataAccess.entities.TeamEntity;
import be.atc.dataAccess.entities.WorkerEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Collection;

public class WorkerService extends ServiceImpl<WorkerEntity> {

    public WorkerService(EntityManager em) {
        super(em);
    }

    public WorkerEntity create(String firstName,
                               String lastName,
                               LocalDate birthdate,
                               String username,
                               String password,
                               SexeType sexeType,
                               RoleEntity role,
                               TeamEntity team) {
        WorkerEntity newWorker = new WorkerEntity();
        newWorker.setFirstName(firstName);
        newWorker.setLastName(lastName);
        newWorker.setBirthdate(birthdate);
        newWorker.setUsername(username);
        newWorker.setPassword(password);
        newWorker.setDeleted(false);
        newWorker.setSexe(sexeType);
        newWorker.setRole(role);
        newWorker.setTeam(team);
        return newWorker;
    }


    @Override
    public boolean hasDuplicate(WorkerEntity w) {
        return (findWorkerByUsernameOrNull(w.getUsername()) != null);
    }

    @Override
    public WorkerEntity findByIdOrNull(int id) {
        return em.find(WorkerEntity.class, id);
    }

    @Override
    public Collection<WorkerEntity> findAllOrNull() {
        try {
            return em.createNamedQuery("Workers.findAll", WorkerEntity.class)
                    .getResultList();
        } catch (NoResultException e) {
            log.debug("The query found no worker to return", e);
            return null;
        }
    }

    public WorkerEntity findWorkerByUsernameOrNull(String username) throws IllegalArgumentException {
        log.debug("Finding worker of username " + username);

        if (username != null && !username.isEmpty()) {
            try {
                return em.createNamedQuery("Workers.findWorkerByLogin", WorkerEntity.class)
                        .setParameter("login", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                log.debug("The query found no worker to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't search worker in database: the username is null or empty");
        }
    }


}

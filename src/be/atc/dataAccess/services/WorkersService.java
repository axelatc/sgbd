package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.WorkersEntity;
import be.atc.test.servlets.ServletTestJpa;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class WorkersService {
    protected EntityManager em;

    private static final Logger log = Logger.getLogger(ServletTestJpa.class);
    public WorkersService(EntityManager em) {
        this.em = em;
    }

    public void save(WorkersEntity w) {
        log.debug("Saving " + w.toString());
        em.getTransaction().begin();
        em.persist(w);
        em.getTransaction().commit();
    }

    public WorkersEntity findWorkerByUsername(String username) throws IllegalArgumentException {
        log.debug("Finding worker of username " + username);
        if (username != null && !username.isEmpty()) {
            return em.createNamedQuery("Workers.findWorkerByLogin", WorkersEntity.class)
                    .setParameter("login", username)
                    .getSingleResult();
        } else {
            throw new IllegalArgumentException("Can't search worker in database: the username is null or empty");
        }
    }

    public void update(WorkersEntity w) {
        log.debug("Updating :" + w.toString());
        em.getTransaction().begin();
        em.merge(w);
        em.getTransaction().commit();
    }

    public void remove(WorkersEntity w) {
        log.debug("Removing :" + w.toString());
        em.getTransaction().begin();
        em.remove(w);
        em.getTransaction().commit();
    }
}

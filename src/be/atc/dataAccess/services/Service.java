package be.atc.dataAccess.services;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class Service<T>{
    protected static final Logger log = Logger.getLogger(Service.class);
    protected EntityManager em;

    public Service(EntityManager em) {
        this.em = em;
    }

    public abstract boolean hasDuplicate(T t);

    public abstract T findByIdOrNull(int id);

    public abstract Collection<T> findAllOrNull();

    public void save(T t) {
        log.debug("Saving " + t.toString());
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    public void update(T t) {
        log.debug("Updating :" + t.toString());
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    public void delete(T t) {
        log.debug("Removing :" + t.toString());
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }
}

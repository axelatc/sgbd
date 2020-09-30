package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.ClockingEntity;
import be.atc.dataAccess.entities.WorkerEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.Collection;

public class ClockingService extends Service<ClockingEntity> {

    public ClockingService(EntityManager em) {
        super(em);
    }

    /*
    //Create a clock-in (startDatetime only)
    public ClockingEntity create(WorkerEntity workersByWorkerId, LocalDateTime startDatetime) {
        ClockingEntity newClocking = new ClockingEntity();
        newClocking.setWorkersByWorkerId(workersByWorkerId);
        newClocking.setStartDatetime(startDatetime);
        return newClocking;
    }*/

    //Update Clocking with endtime
    public void update(int id, LocalDateTime endDatetime) {
        log.debug("Update endTime for clocking id: " + id);
        em.getTransaction().begin();
        em.createNamedQuery("Clocking.updateEndClocking", ClockingEntity.class)
                .setParameter("endDatetime", endDatetime)
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();

    }

    //Find uncompleted clocking
    public ClockingEntity findUncompletedOrNull(WorkerEntity workersByWorkerId) {
        log.debug("Finding uncompleted clocking for worderId: " + workersByWorkerId);
        try {
            log.debug("The query found an uncompleted clocking to return");
            return em.createNamedQuery("Clocking.findUncompletedClocking", ClockingEntity.class)
                    .setParameter("workersByWorkerId", workersByWorkerId)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.debug("The query found no uncompleted clocking to return", e);
            return null;
        }
    }


    @Override
    public boolean hasDuplicate(ClockingEntity clockingEntity) {
        return false;
    }

    @Override
    public ClockingEntity findByIdOrNull(int id) {
        return null;
    }

    @Override
    public Collection<ClockingEntity> findAllOrNull() {
        return null;
    }
}

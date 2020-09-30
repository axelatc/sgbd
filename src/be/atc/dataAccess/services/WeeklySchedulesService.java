package be.atc.dataAccess.services;

import be.atc.dataAccess.entities.ClockingEntity;
import be.atc.dataAccess.entities.WeeklySchedulesEntity;
import be.atc.dataAccess.entities.WorkerEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class WeeklySchedulesService extends Service<WeeklySchedulesEntity>{


    public WeeklySchedulesService(EntityManager em) { super(em); }

    //Find worker actual schedule
    public Collection<WeeklySchedulesEntity> findActualWorkerSchedulesOrNull(WorkerEntity workersByWorkerId, LocalDate actualDate) {
        log.debug("Finding Actual Schedules for worker " + workersByWorkerId);
        try {
            log.debug("The query found schedules");
            return em.createNamedQuery("weeklySchedules.findActualSchedules", WeeklySchedulesEntity.class)
                    .setParameter("workersByWorkerId", workersByWorkerId)
                    .setParameter("actualDate",actualDate)
                    .getResultList();
        } catch (NoResultException e) {
            log.debug("The query found no schedules", e);
            return null;
        }
    }

    //Find update end date actual schedule
    public void update(int id, LocalDateTime endDatetime) {
        log.debug("Update endTime for clocking id: " + id);
        em.getTransaction().begin();
        em.createNamedQuery("Clocking.updateEndClocking", ClockingEntity.class)
                .setParameter("endDatetime", endDatetime)
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();

    }

    @Override
    public boolean hasDuplicate(WeeklySchedulesEntity weeklySchedulesEntity) {
        return false;
    }

    @Override
    public WeeklySchedulesEntity findByIdOrNull(int id) {
        return null;
    }

    @Override
    public Collection<WeeklySchedulesEntity> findAllOrNull() {
        return null;
    }
}

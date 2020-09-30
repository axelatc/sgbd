package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clockings", schema = "projetsgbd", catalog = "")

@NamedQueries({
        //Select last clocking from worker

        @NamedQuery(name = "Clocking.findUncompletedClocking",
                query = "SELECT c " +
                        "FROM ClockingEntity c " +
                        "WHERE c.workersByWorkerId = :workersByWorkerId " +
                        "AND c.endDatetime is null "),


        //Update endDatetime
        @NamedQuery(name = "Clocking.updateEndClocking",
                query = "UPDATE ClockingEntity c " +
                        "SET c.endDatetime = :endDatetime " +
                        "WHERE c.id = :id")

})
/*@NamedNativeQuery(name = "Clocking.createClocking",
        query = "INSERT INTO Clocking" +
                "(" +
                "worker_id," +
                "start_datetime," +
                "VALUES " +
                "(" +
                "?," +
                "?);",
        resultClass = ClockingEntity.class)*/

public class ClockingEntity {
    private int id;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private WorkerEntity workersByWorkerId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "start_datetime", nullable = false)
    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    @Basic
    @Column(name = "end_datetime", nullable = true)
    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockingEntity that = (ClockingEntity) o;
        return id == that.id &&
                Objects.equals(startDatetime, that.startDatetime) &&
                Objects.equals(endDatetime, that.endDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDatetime, endDatetime);
    }

    @ManyToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id", nullable = false)
    public WorkerEntity getWorkersByWorkerId() {
        return workersByWorkerId;
    }

    public void setWorkersByWorkerId(WorkerEntity workersByWorkerId) {
        this.workersByWorkerId = workersByWorkerId;
    }
}

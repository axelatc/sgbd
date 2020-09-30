package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "weekly_schedules", schema = "projetsgbd", catalog = "")

@NamedQueries({
        //Select actual Schedules for worker
        @NamedQuery(name = "weeklySchedules.findActualSchedules",
                query = "SELECT c " +
                        "FROM WeeklySchedulesEntity c " +
                        "WHERE c.workersByWorkerId = :workersByWorkerId " +
                        "AND c.endDate > :actualDate " +
                        "order by c.dayWeek"),
        //Update endDatetime
        @NamedQuery(name = "weeklySchedules.updateEndDate",
                query = "UPDATE WeeklySchedulesEntity c " +
                        "SET c.endDate = :endDate " +
                        "WHERE c.id = :id")
})



public class WeeklySchedulesEntity {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isTraining;
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
    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "day_week", nullable = false)
    public int getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(int dayWeek) {
        this.dayWeek = dayWeek;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "is_training", nullable = false)
    public boolean isTraining() {
        return isTraining;
    }

    public void setTraining(boolean training) {
        isTraining = training;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklySchedulesEntity that = (WeeklySchedulesEntity) o;
        return id == that.id &&
                dayWeek == that.dayWeek &&
                isTraining == that.isTraining &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, dayWeek, startTime, endTime, isTraining);
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

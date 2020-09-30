package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "weekly_plans", schema = "projetsgbd", catalog = "")
public class WeeklyPlansEntity {
    private int id;
    private double hoursPerWeek;
    private Date startDate;
    private Date endDate;
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
    @Column(name = "hours_per_week", nullable = false, precision = 0)
    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyPlansEntity that = (WeeklyPlansEntity) o;
        return id == that.id &&
                Double.compare(that.hoursPerWeek, hoursPerWeek) == 0 &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoursPerWeek, startDate, endDate);
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

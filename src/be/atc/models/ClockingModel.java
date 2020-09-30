package be.atc.models;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.AuthorityEntity;
import be.atc.dataAccess.entities.ClockingEntity;
import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.AuthorityService;
import be.atc.dataAccess.services.ClockingService;
import be.atc.dataAccess.services.RoleService;
import javafx.concurrent.Worker;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.Timestamp;

public class ClockingModel {
    private static final Logger log = Logger.getLogger(ClockingModel.class);
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    private final WorkerEntity worker;
    private final LocalDateTime startDatetime;


    public ClockingModel(WorkerEntity worker, LocalDateTime startDatetime) {
        this.worker = worker;
        this.startDatetime = startDatetime;

    }

    public WorkerEntity getWorkerId() {
        return worker;
    }

    public static LocalDateTime getActualDatetimeAsLocalDateTime() {
        LocalDateTime actualDateTime = LocalDateTime.now();
        String textDateTime = actualDateTime.format(formatter);
        return LocalDateTime.parse(textDateTime, formatter);
    }


    public ClockingEntity getEntity_OrNullIfValidationFailed(ClockingService clockingService) {
        ClockingEntity clockingEntity = new ClockingEntity();
        clockingEntity.setWorkersByWorkerId(worker);
        clockingEntity.setStartDatetime(startDatetime);
        return clockingEntity;
    }


}

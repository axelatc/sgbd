package be.atc.models;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.WeeklySchedulesEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.WeeklySchedulesService;
import be.atc.dataAccess.services.WorkerService;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static be.atc.utils.ValidationUtils.hasContent;


public class WeeklySchedulesModel extends Validator{
    private static final Logger log = Logger.getLogger(WeeklySchedulesModel.class);
    private final String DATE_PATTERN = "yyyy-MM-dd";
    private final String TIME_PATTERN = "HH:mm:ss";
    private final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder().appendPattern(DATE_PATTERN).parseStrict().toFormatter();
    private final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern(TIME_PATTERN).parseStrict().toFormatter();

    private final int workerId;
    private final String startDate;
    private final String endDate;
    private final String startTime;
    private final String endTime;
    private final int dayWeek;

    public WeeklySchedulesModel(int workerId,String startDate,String endDate,String startTime,String endTime,int dayWeek){
        this.workerId=workerId;
        this.startDate=startDate;
        this.endDate=endDate;
        this.startTime=startTime;
        this.endTime=endTime;
        this.dayWeek=dayWeek;
    }


    public LocalDate getStartDateAsLocalDate() {
        return LocalDate.parse(startDate,DATE_FORMATTER);
    }
    public LocalDate getEndDateAsLocalDate() {
        return LocalDate.parse(endDate,DATE_FORMATTER);
    }
    public LocalTime getStartTimeAsLocalTime() {
        return LocalTime.parse(startTime,TIME_FORMATTER);
    }
    public LocalTime getEndTimeAsLocalTime() {
        return LocalTime.parse(endTime,TIME_FORMATTER);
    }

    @Override
    protected List<String> generateAllErrorMessagesOrEmpty() {
        ArrayList<String> allErrorMessages = new ArrayList<String>();
        allErrorMessages.addAll(getErrorMessagesOrEmptyForStartDate());
        allErrorMessages.addAll(getErrorMessagesOrEmptyForEndDate());
        allErrorMessages.addAll(getErrorMessagesOrEmptyForStartTime());
        allErrorMessages.addAll(getErrorMessagesOrEmptyForEndTime());
        allErrorMessages.addAll(getErrorMessagesOrEmptyForDayWeek());
        return allErrorMessages;
    }

    private List<String> getErrorMessagesOrEmptyForStartDate() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        if (!hasContent(startDate)) {
            errorMessages.add("Start date is empty.");
        } else {
            try {
                LocalDate parsedStartDate = LocalDate.parse(startDate, DATE_FORMATTER);
                if(parsedStartDate.isBefore(LocalDate.now())) {
                    errorMessages.add("You have entered a past date for Start Date.");
                }
            } catch (DateTimeParseException e) {
                errorMessages.add("Start date must be at date " + DATE_PATTERN);
            }
        }

        return errorMessages;
    }

    private List<String> getErrorMessagesOrEmptyForEndDate() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        if (!hasContent(endDate)) {
            errorMessages.add("End date is empty");
        } else {
            try {
                LocalDate parsedEndDate = LocalDate.parse(endDate, DATE_FORMATTER);
                if (parsedEndDate.isBefore(LocalDate.now())) {
                    errorMessages.add("You have entered a past date for End Date.");
                }
                else if(parsedEndDate.isBefore(LocalDate.parse(startDate, DATE_FORMATTER))) {
                    errorMessages.add("The end date is after the start date.");
                }
            } catch (DateTimeParseException e) {
                errorMessages.add("End date must be at date " + DATE_PATTERN);
            }
        }

        return errorMessages;
    }

    private List<String> getErrorMessagesOrEmptyForStartTime() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        if (!hasContent(startTime)) {
            errorMessages.add("start Time is empty");
        } else {
            try {
                LocalTime parsedStartTime = LocalTime.parse(startTime,TIME_FORMATTER);
                LocalTime parsedEndTime = LocalTime.parse(endTime,TIME_FORMATTER);
                if (parsedEndTime!=null && parsedStartTime.isAfter(parsedEndTime)){
                    errorMessages.add("Start time is after the end time.");
                }
            } catch (DateTimeParseException e) {
                errorMessages.add("Start Time must be " + DATE_PATTERN);
            }
        }
        return errorMessages;
    }

    private List<String> getErrorMessagesOrEmptyForEndTime() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        if (!hasContent(endTime)) {
            errorMessages.add("End Time is empty");
        } else {
            try {
                LocalTime.parse(endTime,TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                errorMessages.add("StartTime must be " + DATE_PATTERN);
            }
        }
        return errorMessages;
    }

    private List<String> getErrorMessagesOrEmptyForDayWeek() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        if (dayWeek<1 || dayWeek>7) {
            errorMessages.add("Day of the week must be between 1 and 7.");
        }
        return errorMessages;
    }
    public WeeklySchedulesEntity getEntity_OrNullIfValidationFailed(WeeklySchedulesService weeklySchedulesService) {
        if (isValid()) {

            WeeklySchedulesEntity weeklySchedulesEntity = new WeeklySchedulesEntity();
            WorkerService workerService = new WorkerService(EMF.getEM());
            WorkerEntity workerEntity = workerService.findByIdOrNull(workerId);
            weeklySchedulesEntity.setWorkersByWorkerId(workerEntity);
            weeklySchedulesEntity.setStartDate(getStartDateAsLocalDate());
            weeklySchedulesEntity.setEndDate(getEndDateAsLocalDate());
            weeklySchedulesEntity.setStartTime(getStartTimeAsLocalTime());
            weeklySchedulesEntity.setEndTime(getEndTimeAsLocalTime());
            weeklySchedulesEntity.setDayWeek(dayWeek);
            weeklySchedulesEntity.setTraining(false);

            return weeklySchedulesEntity;

        } else {
            return null;
        }
    }

}

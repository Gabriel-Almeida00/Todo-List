package todo.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import todo.list.model.enums.AlarmType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alarm {
    private UUID id;
    private LocalDateTime alarmTime;
    private String description;
    private Integer alarmPeriodMinutes;
    private boolean status;


    public Alarm() {
    }

    public Alarm(LocalDateTime alarmTime, String description, Integer alarmPeriodMinutes) {
        this.id = UUID.randomUUID();
        this.alarmTime = alarmTime;
        this.description = description;
        this.alarmPeriodMinutes = alarmPeriodMinutes;
        this.status = true;
    }

    @JsonIgnore
    public AlarmType getAlarmType() {
        if (alarmTime != null && isStatus()) {
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            LocalDateTime alarmTimeMinusPeriod = alarmTime.minusMinutes(alarmPeriodMinutes);

            if (now.isEqual(alarmTime) && now.isAfter(alarmTime)) {
                return AlarmType.ALARM;
            } else if (now.isEqual(alarmTimeMinusPeriod)) {
                return AlarmType.ALARM_ANTICIPATED;
            } else if (now.isAfter(alarmTimeMinusPeriod) && now.isBefore(alarmTime)) {
                return AlarmType.ALARM_INTERVAL;
            }
        }
        return null;
    }

    public boolean isStatus() {
        return status;
    }

    public void desativarAlarme() {
        status = false;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAlarmPeriodMinutes() {
        return alarmPeriodMinutes;
    }
}

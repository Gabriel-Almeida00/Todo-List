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

    public Alarm() {
    }

    public Alarm(LocalDateTime alarmTime, String description, Integer alarmPeriodMinutes) {
        this.id = UUID.randomUUID();
        this.alarmTime = alarmTime;
        this.description = description;
        this.alarmPeriodMinutes = alarmPeriodMinutes;
    }

    @JsonIgnore
    public AlarmType getAlarmType() {
        if (alarmTime != null) {
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            LocalDateTime alarmTimeMinusPeriod = alarmTime.minusMinutes(alarmPeriodMinutes);

            boolean isAlarmTimeMinusPeriod = now.isEqual(alarmTimeMinusPeriod);
            boolean isAlarmTime = now.isEqual(alarmTime);

            if (isAlarmTimeMinusPeriod) {
                return AlarmType.ALARM_ANTICIPATED;
            } else if (isAlarmTime) {
                return AlarmType.ALARM;
            }
        }
        return null;
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

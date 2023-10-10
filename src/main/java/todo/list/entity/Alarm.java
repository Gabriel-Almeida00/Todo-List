package todo.list.entity;

import todo.list.entity.enums.AlarmType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alarm {
    private UUID id;
    private LocalDateTime alarmTime;
    private String description;
    private Integer alarmPeriodMinutes;

    public Alarm(LocalDateTime alarmTime, String description, Integer alarmPeriodMinutes) {
        this.id = UUID.randomUUID();
        this.alarmTime = alarmTime;
        this.description = description;
        this.alarmPeriodMinutes = alarmPeriodMinutes;
    }


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

    public void setId(UUID id) {
        this.id = id;
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

package todo.list.UI;

import todo.list.entity.Alarm;
import todo.list.entity.Task;
import todo.list.entity.enums.AlarmType;

public interface AlarmObserver {
    void onAlarmTriggered(Task task, Alarm alarm, AlarmType alarmType);
}

package todo.list.observers;

import todo.list.model.Alarm;
import todo.list.model.Task;
import todo.list.model.enums.AlarmType;

public interface AlarmObserver {
     void checkTask(AlarmObserverRegistry observerRegistry);
}

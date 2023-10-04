package todo.list.service.task;

import todo.list.entity.Task;

public interface ITaskParse {
    Task fromStringArray(String[] parts);
    String[] toStringArray(Task task);
}

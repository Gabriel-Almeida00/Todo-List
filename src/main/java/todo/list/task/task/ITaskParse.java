package todo.list.task.task;

import todo.list.entity.Task;

public interface ITaskParse {
    Task fromStringArray(String[] parts);
    String[] toStringArray(Task task);
}

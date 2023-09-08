package todo.list.interfaces;



import todo.list.entity.Task;

import java.util.List;

public interface FileUtil {
    List<String> readData();
    void writeData(List<String> data);

    List<Task> loadTasks();

    void saveTasks(List<Task> tasks);
}

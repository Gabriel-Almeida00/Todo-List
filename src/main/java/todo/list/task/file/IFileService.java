package todo.list.task.file;



import todo.list.entity.Task;

import java.io.IOException;
import java.util.List;

public interface IFileService {
    List<String> readData() throws IOException;
    void writeData(List<String> data) throws IOException;

    List<Task> loadTasks() throws IOException;

    void saveTasks(List<Task> tasks) throws IOException;
}

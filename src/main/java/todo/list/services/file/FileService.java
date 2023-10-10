package todo.list.services.file;


import todo.list.entity.Task;
import todo.list.services.task.TaskParseService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService implements IFileService {
    private final String fileName;
    private final TaskParseService taskParser;

    public FileService(String fileName, TaskParseService taskParser) {
        this.fileName = fileName;
        this.taskParser = taskParser;
    }

    @Override
    public List<String> readData() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    @Override
    public void writeData(List<String> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        List<String> lines = readData();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == taskParser.NUM_FIELDS) {
                Task task = taskParser.fromStringArray(parts);
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public void saveTasks(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            String[] parts = taskParser.toStringArray(task);
            String line = String.join(",", parts);
            lines.add(line);
        }
        writeData(lines);
    }
}

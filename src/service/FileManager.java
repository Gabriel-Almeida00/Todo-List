package service;

import entity.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager implements FileUtil {
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> readData() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public void writeData(List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        List<String> lines = readData();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                String name = parts[0].trim();
                String description = parts[1].trim();
                LocalDateTime deadline = LocalDateTime.parse(parts[2].trim());
                int priority = Integer.parseInt(parts[3].trim());
                String category = parts[4].trim();
                String status = parts[5].trim();
                Task task = new Task(name, description, deadline, priority, category, status);
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void saveTasks(List<Task> tasks) {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.getName() + "," + task.getDescription() + "," + task.getDeadline() + ","
                    + task.getPriority() + "," + task.getCategory() + "," + task.getStatus());
        }
        writeData(lines);
    }
}

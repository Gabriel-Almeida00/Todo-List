package repository;

import entity.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskFileHandler {
    private String fileName;


    public TaskFileHandler(String fileName) {
        this.fileName = fileName;
        createFileIfNotExists(); // Criar o arquivo se ele não existir
    }

    private void createFileIfNotExists() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveTasksToFile(List<Task> tasks) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> readTasksFromFile() {
        List<Task> tasks = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            tasks = (List<Task>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}

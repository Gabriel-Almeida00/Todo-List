package todo.list.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import todo.list.model.Task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class JsonData implements IJsonData {
    private final String fileName;
    private final ObjectMapper objectMapper;


    public JsonData(String fileName) {
        this.fileName = fileName;
        this.objectMapper =
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }


    @Override
    public List<Task> loadTasks() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Task.class);
            return objectMapper.readValue(fileInputStream, listType);
        }
    }

    @Override
    public void saveTasks(List<Task> tasks) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            objectMapper.writeValue(fileOutputStream, tasks);
        }
    }
}

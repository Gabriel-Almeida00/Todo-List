package service;


import java.util.List;

public interface FileUtil {
    List<String> readData();
    void writeData(List<String> data);
}

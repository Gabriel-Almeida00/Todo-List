package todo.list.observers.bot;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import todo.list.exception.FileException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigTelegram {
    private String botUsername;
    private String token;

    public ConfigTelegram() {
        loadFromJson();
    }

    private void loadFromJson() {
        try{
            FileReader reader = new FileReader("telegram.json");
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();

            token = config.get("token").getAsString();
            botUsername = config.get("botUsername").getAsString();
        } catch (FileNotFoundException e){
            throw new FileException("Arquivo não encontrado" + e.getMessage());
        }

    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getToken() {
        return token;
    }
}

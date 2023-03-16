package network;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    private final String URL = "https://www.cbr-xml-daily.ru/daily_json.js";


    public JSONObject readFromUrl(String URL) {
        StringBuilder builder = new StringBuilder();
        try (InputStream inputStream = new URL(URL).openStream();
             BufferedReader reader = new BufferedReader
                     (new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                builder.append((char) reader.read());
            }
            return new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

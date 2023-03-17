package currencies;


import android.os.StrictMode;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    private final String URL = "https://www.cbr-xml-daily.ru/daily_json.js";


    public JSONObject readFromUrl() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(URL);
            String json = IOUtils.toString(url, StandardCharsets.UTF_8);
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getCurrencyValue(Currency currency) {
        double value;
        JSONObject jsonObject = readFromUrl();
        if (jsonObject == null) {
            return false;
        }
        try {
            value = jsonObject.getJSONObject("Valute").getJSONObject(currency.getName()).getDouble("Value");
            currency.setValue(value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

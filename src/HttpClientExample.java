import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class HttpClientExample {
    public static void main(String[] args) {
        // Ваш API-ключ
        String apiKey = "a7f5fceb-9d25-476b-81ff-e367042f316c";
        String urlString = "https://api.weather.yandex.ru/v2/forecast?lat=55.5&lon=37.2&limit=7";

        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Установка заголовка с API-ключом
            connection.setRequestProperty("X-Yandex-Weather-Key", apiKey);

            // Чтение ответа
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Парсинг JSON-ответа
            JSONObject jsonResponse = new JSONObject(response.toString());
            System.out.println("Полный ответ от API:");
            System.out.println(jsonResponse.toString(2)); // Красивый вывод JSON

            // Получение текущей температуры
            int currentTemp = jsonResponse.getJSONObject("fact").getInt("temp");
            System.out.println("Текущая температура: " + currentTemp + "°C");

            // Вычисление средней температуры за период
            double totalTemp = 0;
            int count = 0;

            for (Object forecast : jsonResponse.getJSONArray("forecasts")) {
                JSONObject day = ((JSONObject) forecast).getJSONObject("parts").getJSONObject("day");
                if (day.has("temp_avg")) {
                    totalTemp += day.getInt("temp_avg");
                    count++;
                }
            }

            double avgTemp = totalTemp / count;
            System.out.println("Средняя температура за " + count + " дней: " + avgTemp + "°C");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
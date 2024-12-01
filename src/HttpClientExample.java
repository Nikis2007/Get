import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class HttpClientExample {
    public static void main(String[] args) {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

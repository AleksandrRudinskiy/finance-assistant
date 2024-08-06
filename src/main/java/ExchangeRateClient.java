import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateClient {

    private String RATES_URI = "https://functions.yandexcloud.net/d4ed1i6t3f80hf0p7mer?base=RUB&symbols=USD,EUR,JPY";
    private List<Double> currentExchangeRates = new ArrayList<>();

    public ExchangeRateClient() {
        getRatesFromClient();
    }

    public double getRateUSD() {
        return currentExchangeRates.get(0);
    }

    public double getRateEUR() {
        return currentExchangeRates.get(1);
    }

    public double getURateJPY() {
        return currentExchangeRates.get(2);
    }

    private void getRatesFromClient() {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(RATES_URI);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println(response.body());
                JsonElement jsonElement = JsonParser.parseString(response.body());
                if (!jsonElement.isJsonObject()) {
                    System.out.println("Ответ от сервера не соответствует ожидаемому.");
                    return;
                }
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonObject jsonObject1 = jsonObject.get("rates").getAsJsonObject();

                double rateUSD = 1 / jsonObject1.get("USD").getAsDouble();
                currentExchangeRates.add(rateUSD);
                double rateEUR = 1 / jsonObject1.get("EUR").getAsDouble();
                currentExchangeRates.add(rateEUR);
                double rateJPY = 1 / jsonObject1.get("JPY").getAsDouble();
                currentExchangeRates.add(rateJPY);
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }
}

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ConversorCambio {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final Map<String, String> MOEDAS = new HashMap<>();

    static {
        MOEDAS.put("USD", "Dólar Americano");
        MOEDAS.put("EUR", "Euro");
        MOEDAS.put("GBP", "Libra Esterlina");
        MOEDAS.put("BRL", "Real Brasileiro");
        MOEDAS.put("CNY", "Renminbi Chinês");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            bemVindo();
            mostrarMoedasDisponiveis();

            String moedaOrigem = solicitarMoeda(scanner, "origem");
            String moedaDestino = solicitarMoeda(scanner, "destino");

            processarConversao(scanner, moedaOrigem, moedaDestino);
        }
    }

    private static void bemVindo() {
        System.out.println("Bem-vindo ao Conversor de Câmbio!");
    }

    private static void mostrarMoedasDisponiveis() {
        System.out.println("Moedas disponíveis:");
        MOEDAS.forEach((sigla, nome) -> System.out.println(sigla + " - " + nome));
    }

    private static String solicitarMoeda(Scanner scanner, String tipo) {
        while (true) {
            System.out.print("Digite a sigla da moeda de " + tipo + ": ");
            String moeda = scanner.next().toUpperCase();
            if (MOEDAS.containsKey(moeda)) {
                return moeda;
            }
            System.out.println("Moeda de " + tipo + " inválida. Tente novamente.");
        }
    }

    private static void processarConversao(Scanner scanner, String moedaOrigem, String moedaDestino) {
        try {
            double taxaCambio = getTaxaCambio(moedaOrigem, moedaDestino);
            System.out.print("Digite o valor em " + MOEDAS.get(moedaOrigem) + ": ");
            double valorOrigem = scanner.nextDouble();
            double valorDestino = valorOrigem * taxaCambio;
            System.out.println("Valor em " + MOEDAS.get(moedaDestino) + ": " + valorDestino);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Erro ao acessar o serviço de câmbio: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static double getTaxaCambio(String moedaOrigem, String moedaDestino) throws IOException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL + moedaOrigem))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            try (JsonReader jsonReader = Json.createReader(new StringReader(response.body()))) {
                JsonObject jsonObject = jsonReader.readObject();
                JsonObject rates = jsonObject.getJsonObject("rates");
                if (rates.containsKey(moedaDestino)) {
                    return rates.getJsonNumber(moedaDestino).doubleValue();
                } else {
                    throw new RuntimeException("Moeda de destino não encontrada nos dados disponíveis.");
                }
            }
        } else {
            throw new RuntimeException("Erro ao obter taxa de câmbio: Status " + response.statusCode());
        }
    }
}

package ConversorDeMonedas;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Scanner;

public class conversionMonedas {

    public static void  main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("*creado por Camila Torres*");
            System.out.println("***************************************");
            System.out.println(" \uD83D\uDCB2 ¡Bienvenido/a al Conversor de Monedas! \uD83D\uDCB2");
            System.out.println("****************************************");
            System.out.println("1) USD a ARS (Peso argentino.)");
            System.out.println("2) USD a BOB (Boliviano boliviano.)");
            System.out.println("3) USD a BRL (Real brasileño.)");
            System.out.println("4) USD a CLP (Peso chileno.)");
            System.out.println("5) USD a COP (Peso colombiano.)");
            System.out.println("6) Salir");
            System.out.print("Elija una opción válida: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> convertir(scanner, "USD", "ARS");
                case 2 -> convertir(scanner, "USD", "BOB");
                case 3 -> convertir(scanner, "USD", "BRL");
                case 4 -> convertir(scanner, "USD", "CLP");
                case 5 -> convertir(scanner, "USD", "COP");
                case 6 -> System.out.println(" salir del programa...");
                default -> System.out.println("Opción no válida.");
            }

            System.out.println();

        } while (opcion != 6);

        scanner.close();
    }

    public static void convertir(Scanner scanner, String de, String a) {
        System.out.print("Ingrese el monto en " + de + ": ");
        double monto = scanner.nextDouble();
        double tasa = obtenerTasaCambio(de, a);

        if (tasa == 0.0) {
            System.out.println("No se pudo obtener la tasa. Intente nuevamente.");
        } else {
            double resultado = monto * tasa;
            System.out.printf("Resultado: %.2f %s = %.2f %s\n", monto, de, resultado, a);
        }
    }

    public static double obtenerTasaCambio(String base, String destino) {
        String apiKey = "4f714a3674b6930b53736ba0";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + base;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject conversionRates = json.getAsJsonObject("conversion_rates");

            return conversionRates.get(destino).getAsDouble();

        } catch (Exception e) {
            System.out.println("Error al obtener la tasa: " + e.getMessage());
            return 0.0;

        }
    }
}






package com.rllivescore;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class PandaScoreAPIClient {
    public static String fetchApi() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.pandascore.co/rl/matches/upcoming?sort=begin_at&page=1&per_page=50"))
                    .header("Authorization", "50MVwnUvvD6cPiDt_bkU_gb8cghL08C3ExQzViGQl6B0hPqKZzA")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

            // System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Feil ved API-forespørsel: " + e.getMessage());
            return "{}";
        }

    }
}

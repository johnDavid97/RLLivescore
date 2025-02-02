package com.rllivescore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiParser {
    public static void parseJson(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            if (rootNode.isArray() && rootNode.size() > 0) {
                JsonNode firstMatch = rootNode.get(0);
                String leagueName = firstMatch.path("league").path("name").asText();

                String matchName = firstMatch.path("name").asText();
                String status = firstMatch.path("status").asText();
                String beginAt = firstMatch.path("begin_at").asText();

                JsonNode opponents = firstMatch.path("opponents");
                String teamA = opponents.get(0).path("opponent").path("name").asText();
                String teamB = opponents.get(1).path("opponent").path("name").asText();

                System.out.println("League: " + leagueName);
                System.out.println("Match: " + matchName);
                System.out.println("Status: " + status);
                System.out.println("Starttid: " + beginAt);
                System.out.println("Lag: " + teamA + " vs " + teamB);

            } else {
                System.out.println("Ingen kamper funnet i JSON-dataen.");
            }

        } catch (Exception e) {
            System.out.println("Feil ved parsing av JSON: " + e.getMessage());
        }
    }
}

package com.rllivescore;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class MongoRetrieveData {
    public static List<Document> displayData() {
        MongoDatabase database = MongoDBClient.getDatabase();
        MongoCollection<Document> collection = database.getCollection("matches");

        List<Document> matches = new ArrayList<>();
        for (Document doc : collection.find()) {

            Object streamsObject = doc.get("streams_list");
            List<Document> streams = new ArrayList<>();

            if (streamsObject instanceof List<?>) {
                for (Object obj : (List<?>) streamsObject) {
                    if (obj instanceof Document) {
                        streams.add((Document) obj);
                    }
                }
            }

            String streamUrl = (!streams.isEmpty())
                    ? streams.get(0).getString("raw_url")
                    : "No stream available";

            Document formattedMatch = new Document()
                    .append("match_id", doc.get("id"))
                    .append("name", doc.getString("name"))
                    .append("league", ((Document) doc.get("league")).getString("name"))
                    .append("tournament", ((Document) doc.get("tournament")).getString("name"))
                    .append("teams", doc.get("opponents"))
                    .append("score", doc.get("results"))
                    .append("stream", streamUrl) // Riktig plassering av stream
                    .append("scheduled_at", doc.getString("scheduled_at"))
                    .append("status", doc.getString("status"));

            matches.add(formattedMatch);
        }
        return matches;
    }
}

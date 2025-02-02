package com.rllivescore;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerApp {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "pandaScore-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        MongoDatabase database = MongoDBClient.getDatabase();
        MongoCollection<Document> collection = database.getCollection("matches");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("test-topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {

                System.out.println("Received: " + record.value());

                try {
                    List<Document> documents = new ArrayList<>();

                    // Pakker inn JSON-arrayet i et objekt for å kunne parse
                    for (Object obj : Document.parse("{ \"data\": " + record.value() + " }").getList("data",
                            Document.class)) {
                        documents.add((Document) obj);
                    }

                    // Setter inn dokumentene i MongoDB
                    if (!documents.isEmpty()) {
                        collection.insertMany(documents);
                        System.out.println("Saved to MongoDB: " + documents);
                    } else {
                        System.out.println("Received empty list, nothing to save.");
                    }

                } catch (JsonParseException e) {
                    System.err.println("JSON Parsing Error: " + e.getMessage());
                }
            }
        }
    }
}

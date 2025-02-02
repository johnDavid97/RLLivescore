package com.rllivescore;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class KafkaProducerApp {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerApp.class);

    public static void main(String[] args) {
        logger.info("Starting Kafka Producer...");

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "Hello, Kafka!");
        producer.send(record);

        producer.close();
    }

}

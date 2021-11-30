package br.com.g12soccer.infrastructure.messaging;

import br.com.g12soccer.domain.util.GsonDeserializer;
import br.com.g12soccer.domain.util.Topics;
import br.com.g12soccer.infrastructure.messaging.consumers.CreatePlayerConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaService<T> implements Closeable {

    final KafkaConsumer<String, String> consumer;
    final ConsumerFunction parse;

    public KafkaService(String topic, ConsumerFunction parse, Class<T> type) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(type));
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void run() {

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Finding total records: " + records.count());
                for (var record: records) {
                    parse.consume(record);
                }
            }
        }

    }

    Properties properties(Class<T> type) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,  StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, CreatePlayerConsumer.class.getSimpleName());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.toString());


        return properties;
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }
}

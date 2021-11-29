package br.com.g12soccer.infrastructure.messaging.producers;

import br.com.g12soccer.domain.util.Topics;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CreatePlayerProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
         var producer = new KafkaProducer<String, String>(properties());
         var value = "RICAS";
         var record = new ProducerRecord<>(Topics.CREATE_PLAYER.toString(), value, value);
         producer.send(record, (data, ex) -> {
             if (ex !=null) {
                 ex.printStackTrace();
                 return;
             }
             System.out.println(data.topic());
         }).get();

    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}

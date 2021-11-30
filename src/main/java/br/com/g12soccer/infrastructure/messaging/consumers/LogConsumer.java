package br.com.g12soccer.infrastructure.messaging.consumers;

import br.com.g12soccer.domain.Log;
import br.com.g12soccer.domain.util.Topics;
import br.com.g12soccer.infrastructure.messaging.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogConsumer {

    public static void main(String[] args) {
        var logConsumer = new LogConsumer();
        var service = new KafkaService(Topics.CREATE_PLAYER.toString(), logConsumer::parse, Log.class);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println(" LOGGING INFO: ");
        System.out.println("KEY: " + record.key());
        System.out.println("VALUE: " + record.value());
        System.out.println("PARTITION: " + record.partition());
        System.out.println("OFFSET: " + record.offset());

    }

}

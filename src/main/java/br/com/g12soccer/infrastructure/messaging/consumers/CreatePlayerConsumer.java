package br.com.g12soccer.infrastructure.messaging.consumers;

import br.com.g12soccer.domain.Player;
import br.com.g12soccer.domain.util.Topics;
import br.com.g12soccer.infrastructure.messaging.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreatePlayerConsumer {

    public static void main(String[] args) {
        var playerConsumer = new CreatePlayerConsumer();
        var service = new KafkaService(Topics.CREATE_PLAYER.toString(), playerConsumer::parse, Player.class);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("KEY: " + record.key());
        System.out.println("VALUE: " + record.value());
        System.out.println("PARTITION: " + record.partition());
        System.out.println("OFFSET: " + record.offset());

    }


}

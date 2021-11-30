package br.com.g12soccer.infrastructure.messaging.producers;

import br.com.g12soccer.domain.Player;
import br.com.g12soccer.domain.util.Topics;
import br.com.g12soccer.infrastructure.messaging.KafkaDispatcher;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CreatePlayerProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var dispatcher = new KafkaDispatcher<Player>();
        var player = new Player(UUID.randomUUID().toString(), "Ricardo 4512");

        for (int i=0; i<50; i++) {
            player.setId(" id " + i);
            dispatcher.send(Topics.CREATE_PLAYER.toString(), "key", player);

        }

    }

}

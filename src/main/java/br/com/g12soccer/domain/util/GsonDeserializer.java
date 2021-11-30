package br.com.g12soccer.domain.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {

    private Class<T> type;
    public static final String TYPE_CONFIG = "br.com.g12soccer.domain.util.type_config";
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("can`t deserialize: " + e.getMessage());
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        return gson.fromJson(new String(data), type);
    }
}

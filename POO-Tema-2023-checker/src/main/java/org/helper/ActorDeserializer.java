package org.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.Setter;
import org.example.Actor;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActorDeserializer extends StdDeserializer<Actor> {

    public ActorDeserializer() {
        this(null);
    }

    public ActorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Actor deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String name = node.has("name") ? node.get("name").asText() : null;
        String biography = node.has("biography") ? node.get("biography").asText() : null;

        List<Map.Entry<String, String>> performances = new ArrayList<>();
        JsonNode performancesNode = node.get("performances");

        if (performancesNode != null && performancesNode.isArray()) {
            for (JsonNode entryNode : performancesNode) {
                String title = entryNode.has("title") ? entryNode.get("title").asText() : null;
                String type = entryNode.has("type") ? entryNode.get("type").asText() : null;
                if (title != null && type != null) {
                    performances.add(new SimpleEntry<>(title, type));
                }
            }
        }

        return new Actor(name, performances, biography);
    }

    private static class SimpleEntry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;

        public SimpleEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public String toString() {
            return "\ntitle: " + key + "\ntype: " + value + "\n";
        }
    }
}

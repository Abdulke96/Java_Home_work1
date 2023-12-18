package org.helper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.example.Movie;
import org.example.Production;
import org.example.Series;

import java.io.IOException;

public class ProductionDeserializer extends StdDeserializer<Production> {

    public ProductionDeserializer() {
        this(null);
    }

    public ProductionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Production deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        // Extract type information
        String type = node.get("type").asText();

        // Use type information to instantiate the appropriate subclass
        return switch (type) {
            case "Movie" -> jp.getCodec().treeToValue(node, Movie.class);
            case "Series" -> jp.getCodec().treeToValue(node, Series.class);
            default -> throw new UnsupportedOperationException("Unsupported production type: " + type);
        };
    }
}


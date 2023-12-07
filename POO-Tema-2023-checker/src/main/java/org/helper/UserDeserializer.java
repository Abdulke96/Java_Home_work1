package org.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.example.Admin;
import org.example.Contributor;
import org.example.Regular;
import org.example.User;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        // Extract necessary information from the JSON node and instantiate the correct subclass
        // Example: Assuming you have a field "userType" indicating the concrete subclass
        String userType = node.get("userType").asText();
        if ("Regular".equals(userType)) {
            return jp.getCodec().treeToValue(node, Regular.class);
        } else if ("Contributor".equals(userType)) {
            return jp.getCodec().treeToValue(node, Contributor.class);
        } else if ("Admin".equals(userType)) {
            return jp.getCodec().treeToValue(node, Admin.class);
        }
        // Handle other cases or throw an exception if needed
        throw new UnsupportedOperationException("Unknown user type: " + userType);
    }
}

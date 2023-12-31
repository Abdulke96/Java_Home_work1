//package org.helper;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//import java.io.IOException;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//public class ContributionsHandler<T extends Comparable<T>> extends JsonDeserializer<SortedSet<T>> {
//
//    private final Class<T> elementType;
//
//    public ContributionsHandler() {
//        this.elementType = null;
//    }
//    public ContributionsHandler(Class<T> elementType) {
//        this.elementType = elementType;
//    }
//
//    @Override
//    public SortedSet<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode node = mapper.readTree(p);
//
//        SortedSet<T> contributions = new TreeSet<>();
//
//        addContributions(node, "productionsContribution", contributions, mapper);
//        addContributions(node, "actorsContribution", contributions, mapper);
//
//        return contributions;
//    }
//
//    private void addContributions(ObjectNode node, String fieldName, SortedSet<T> contributions, ObjectMapper mapper) {
//        if (node.has(fieldName)) {
//            ArrayNode arrayNode = (ArrayNode) node.get(fieldName);
//            for (JsonNode elementNode : arrayNode) {
//                T contribution = mapper.convertValue(elementNode, elementType);
//                contributions.add(contribution);
//            }
//        }
//    }
//}

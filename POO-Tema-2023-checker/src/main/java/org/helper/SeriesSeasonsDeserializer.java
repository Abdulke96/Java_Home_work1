package org.helper;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.example.Episode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class SeriesSeasonsDeserializer extends StdDeserializer<Map<String, List<Episode>>> {
//
//    public SeriesSeasonsDeserializer() {
//        this(null);
//    }
//
//    public SeriesSeasonsDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public Map<String, List<Episode>> deserialize(JsonParser jp, DeserializationContext ctxt)
//            throws IOException, JsonProcessingException {
//        JsonNode node = jp.getCodec().readTree(jp);
//
//        Map<String, List<Episode>> seasons = new HashMap<>();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode seasonsNode = node.get("seasons");
//
//        if (seasonsNode != null && seasonsNode.isObject()) {
//            for (JsonNode seasonNode : seasonsNode) {
//                String seasonName = seasonNode.fieldNames().next();
//                JsonNode episodesNode = seasonNode.get(seasonName);
//
//                if (episodesNode != null && episodesNode.isArray()) {
//                    List<Episode> episodes = new ArrayList<>();
//                    for (JsonNode episodeNode : episodesNode) {
//                        Episode episode = objectMapper.treeToValue(episodeNode, Episode.class);
//                        episodes.add(episode);
//                    }
//                    seasons.put(seasonName, episodes);
//                }
//            }
//        }
//
//        return seasons;
//    }
//}


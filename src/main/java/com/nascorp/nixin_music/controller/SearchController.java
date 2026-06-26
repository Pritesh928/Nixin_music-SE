package com.nascorp.nixin_music.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nascorp.nixin_music.entity.SearchResult;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SearchController(RestTemplate restTemplate) { 
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String q) throws Exception {

        String apiKey = "AIzaSyC-YqneM0ndl0xlsPARk64B-wuoPNrHgLY";

        String youtubeUrl = "https://www.googleapis.com/youtube/v3/search"
                + "?part=snippet"
                + "&type=video"
                + "&maxResults=10"
                + "&q=" + q
                + "&key=" + apiKey;

        String rawJson = restTemplate.getForObject(youtubeUrl, String.class);

        JsonNode root = objectMapper.readTree(rawJson);
        JsonNode items = root.get("items");
        
        List<SearchResult> results = new ArrayList<>();

        for(JsonNode item : items) {

            JsonNode idNode = item.get("id");
            if (idNode == null || idNode.get("videoId") == null) {
                continue;
            }
            String videoId = idNode.get("videoId").asText();
            
            String title = item.get("snippet").get("title").asText();

            String thumbnail = item.get("snippet").get("thumnails").get("high").get("url").asText();

            results.add(new SearchResult(videoId, title, thumbnail));
        }

        return results;
    }
}

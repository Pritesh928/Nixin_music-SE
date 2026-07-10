package com.nascorp.nixin_music.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

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

    @Value("${youtube.api.key}")
    private String apiKey;

    
    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String q) throws Exception {

        String youtubeUrl = "https://www.googleapis.com/youtube/v3/search"
                + "?part=snippet"
                + "&type=video"
                + "&maxResults=50"
                + "&q=" + q
                + "&key=" + apiKey;

        String rawJson = restTemplate.getForObject(youtubeUrl, String.class);

        JsonNode root = objectMapper.readTree(rawJson);
        JsonNode items = root.get("items");
        
        List<SearchResult> results = new ArrayList<>();

        for (JsonNode item : items) {

            JsonNode idNode = item.get("id");
            if(idNode == null || idNode.get("videoId") == null) continue;

            JsonNode snippet = item.get("snippet");
            if(snippet == null) continue;

            String videoId = idNode.get("videoId").asText();
            String title = snippet.get("title").asText();

            String thumbnail = "";
            JsonNode thumbnails = snippet.get("thumbnails");
            if(thumbnails != null) {
                if(thumbnails != null) {
                    thumbnail = thumbnails.get("high").get("url").asText();
                }else if(thumbnails.get("medium") != null) {
                    thumbnail = thumbnails.get("medium").get("url").asText();
                }else if(thumbnails.get("default") != null) {
                    thumbnail = thumbnails.get("default").get("url").asText();
                }
            results.add(new SearchResult(videoId, title, thumbnail));
            }
        }

        return results;
    }

    @GetMapping("/stream")
    public ResponseEntity<String> getStreamUrl(@RequestParam String id) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp",
                "-f", "bestaudio",
                "--get-url",
                "https://www.youtube.com/watch?v=" + id
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            String streamUrl = new String(process.getInputStream().readAllBytes()).trim();
            process.waitFor();

            if(streamUrl.isEmpty() || streamUrl.startsWith("Error!!")) {
                return ResponseEntity.status(500).body("Failed to get the stream url :( ");
            }

            return ResponseEntity.ok(streamUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error : " + e.getMessage());
        }
    }
}

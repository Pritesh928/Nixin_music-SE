package com.nascorp.nixin_music.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final RestTemplate restTemplate;

    public SearchController(RestTemplate restTemplate) { 
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/search")
    public String getQuery(@RequestParam String q){

        String apiKey = "YOUR_YOUTUBE_DATA_API_KEY";

        String youtubeUrl = "https://www.googleapis.com/youtube/v4/search"
                + "?part=snippet"
                + "&type=video"
                + "&maxResults=10"
                + "&q=" + q
                + "&key=" + apiKey;

        String response = restTemplate.getForObject(youtubeUrl, String.class);

        return response;
    }
}

package com.nascorp.nixin_music.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api/search")
@RestController
public class SearchController {
    
    @GetMapping("id")
    public String getID(@RequestParam int q){
        return "Query : " + q;
    }

    @GetMapping("fname")
    public String getName(@RequestParam String name){
        return "Name : " + name;
    }
    
}

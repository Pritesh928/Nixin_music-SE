package com.nascorp.nixin_music.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("api")
@RestController
public class Healthcheck {

    @GetMapping("hello")
    private String hello(){
        return "<h1>hello there>";
    }
    
}
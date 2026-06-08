package com.nascorp.nixin_music.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Healthcheck {

    @GetMapping("hello")
    private String hello(){
        return "<h1>hello there";
    }

    
}
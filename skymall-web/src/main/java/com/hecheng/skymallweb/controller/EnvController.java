package com.hecheng.skymallweb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/env")
@Controller
public class EnvController {

    @Value("${env}")
     private String currentEnv;

    @GetMapping("/print")
    public String print(){

        return currentEnv;

    }
}

package com.hecheng.skymallweb.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${server.port}")
    String port;


    @PostMapping(value = "/hello")
    @ResponseBody
    public String  toIndex(@RequestParam String name){

        return  "hello  "+name+"  welcome to skymall , current port is "+port;
    }




}

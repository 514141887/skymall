package com.hecheng.skymallweb.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动加载初始化某些类
 */
@Component
public class InitData implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        this.init();
    }


    private void init() {

        //TODO 初始化加载你需要的东西

    }
}

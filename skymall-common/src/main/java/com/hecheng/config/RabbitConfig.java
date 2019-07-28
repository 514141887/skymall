package com.hecheng.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置
 * @author liqiang
 *
 */
@Configuration
public class RabbitConfig {

	/**
	 * 创建队列
	 * @return
	 */
    @Bean
    public Queue Queue() {
    	
        return new Queue("hello");
    }

}

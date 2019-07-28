package com.hecheng.config;

import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *
 * @author liqiang
 *
 * 	maxInactiveIntervalInSeconds 默认1800秒过期，如需修改时间，使用以下注解
 *	@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
 *	单位为秒
 */
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class HttpSessionConfig {
}
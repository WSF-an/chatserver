package org.example.chatserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@SpringBootTest
class ChatServerApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws InterruptedException {
        System.out.println(redisTemplate);
        redisTemplate.opsForValue().set("name", "小明");
        System.out.println(redisTemplate.opsForValue().get("name"));
        redisTemplate.opsForValue().set("test","123456",3, TimeUnit.SECONDS);
        sleep(4000);
        System.out.println(redisTemplate.opsForValue().get("test"));
        redisTemplate.opsForValue().setIfAbsent("lock",1);
        redisTemplate.opsForValue().setIfAbsent("lock",2);
        System.out.println(redisTemplate.opsForValue().get("lock"));
    }

}

package com.ahogek.myblogserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = MyBlogServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyBlogServerApplicationTests {

    @Test
    void contextLoads() {
        log.info("测试");
    }
}

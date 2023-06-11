package com.ahogek.myblogserver;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = MyBlogServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyBlogServerApplicationTests {

    private final OkHttpClient client = new OkHttpClient();
    @LocalServerPort
    private int port;
    @Autowired
    private Environment env;
    private HttpUrl baseUrl;

    @BeforeAll
    void setup() {
        String host = env.getProperty("local.server.host", "localhost");
        String contextPath = env.getProperty("server.servlet.context-path", "/");
        baseUrl = new HttpUrl.Builder().scheme("http").host(host).port(port)
                .addPathSegments(contextPath.substring(1)).build();
    }

    @Test
    void checkBaseUrlNonNull() {
        Assertions.assertNotNull(baseUrl);
        log.info("\nbaseUrl: {}", baseUrl);
    }

    @Test
    void helloWorld() {
        Request request = new Request.Builder()
                .url(baseUrl.newBuilder()
                        .addQueryParameter("name", "AhogeK")
                        .addPathSegments("hello").build())
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            Assertions.assertNotNull(response.body());
            String result = response.body().string();
            Assertions.assertEquals("Hello AhogeK!", result);
            log.info("\n{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

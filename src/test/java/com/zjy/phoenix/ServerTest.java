package com.zjy.phoenix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.WebServer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: myproject01
 * @description:
 * @author: ZhangJianYong
 * @create: 2019-04-12 10:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerTest {
    private Logger logger = LoggerFactory.getLogger(ServerTest.class);
    @Autowired
    org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext ServletWebServerApplicationContext;

    @Test
    public void contextLoads() {
        WebServer webServer = ServletWebServerApplicationContext.getWebServer();
        logger.info(webServer.toString());
    }
}

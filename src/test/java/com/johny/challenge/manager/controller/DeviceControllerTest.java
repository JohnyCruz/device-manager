package com.johny.challenge.manager.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    public TestRestTemplate restTemplate;

    @Test
    public void testHelloWorld() throws Exception{
        assertTrue(restTemplate.getForObject("http://localhost:"+port+"/devices/", String.class).equals("Hello World!"));
    }

}

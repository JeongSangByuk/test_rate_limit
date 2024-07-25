package com.test.ratelimit.test;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class TestController {

    private final String instanceId = UUID.randomUUID().toString();

    @GetMapping("/test")
    public String test() {

        return "test new : " + instanceId;
    }


}

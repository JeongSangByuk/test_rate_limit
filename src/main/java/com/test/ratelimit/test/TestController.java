package com.test.ratelimit.test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @GetMapping("/test")
    @LimitRequestPerTime(
            prefix="prefix:",
            ttl=1,
            ttlTimeUnit = TimeUnit.SECONDS,
            count=100
    )
    public String test() {




        return "test";
    }


}

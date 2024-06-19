package com.test.ratelimit.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequestPerTime {

    // 분당호출 제한시킬 unique key prefix
    String prefix();

    //호출 제한 시간
    int ttl();

    // 호출 제한 시간 단위
    TimeUnit ttlTimeUnit();

    // 분당 호출제한 카운트
    int count();

}
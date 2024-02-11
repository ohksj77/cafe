package com.kimseungjin.cafe.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Aspect
@Component
public class LogIntroduction {
    private static final String LOG_FORMAT = "METHOD : {}";

    @Pointcut("execution(* com.kimseungjin.cafe..*Controller*.*(..))")
    public void allController() {}

    @Pointcut("execution(* com.kimseungjin.cafe..*Service*.*(..))")
    public void allService() {}

    @Pointcut("execution(* com.kimseungjin.cafe..*Repository*.*(..))")
    public void allRepository() {}

    @Before("allController()")
    public void controllerLog(final JoinPoint joinPoint) {
        logging(joinPoint, log::info);
    }

    @Before("allService() || allRepository()")
    public void serviceAndRepositoryLog(final JoinPoint joinPoint) {
        logging(joinPoint, log::debug);
    }

    private void logging(final JoinPoint joinPoint, final BiConsumer<String, String> consumer) {
        consumer.accept(LOG_FORMAT, joinPoint.getSignature().toShortString());
    }
}

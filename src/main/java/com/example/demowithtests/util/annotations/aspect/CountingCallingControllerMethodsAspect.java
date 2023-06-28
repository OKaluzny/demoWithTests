package com.example.demowithtests.util.annotations.aspect;

import com.example.demowithtests.util.EndpointCallingCounter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CountingCallingControllerMethodsAspect {

    @Pointcut("execution(public * com.example.demowithtests.web.EmployeeController.*(..))")
    public void callAtEmployeeController() {
    }

    @After("callAtEmployeeController()")
    public void callAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        EndpointCallingCounter.increment(methodName);
    }
}

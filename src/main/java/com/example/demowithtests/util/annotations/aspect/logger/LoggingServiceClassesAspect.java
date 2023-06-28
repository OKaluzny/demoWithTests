package com.example.demowithtests.util.annotations.aspect.logger;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.example.demowithtests.util.annotations.aspect.logger.LogColourConstant.*;

@Log4j2
@Aspect
@Component
public class LoggingServiceClassesAspect {

    @Pointcut("execution(public * com.example.demowithtests.service.EmployeeServiceBean.*(..))")
    public void callAtEmployeeServicePublicMethods() {
    }

    @Before("callAtEmployeeServicePublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        String argsLog = args.length > 0 ? "Args count - " + args.length : "";
        log.debug(ANSI_BLUE + "Service: " + methodName + " - start." + argsLog + ANSI_RESET);
    }

    @AfterReturning(value = "callAtEmployeeServicePublicMethods()", returning = "returningValue")
    public void logAfter(JoinPoint joinPoint, Object returningValue) {
        String methodName = joinPoint.getSignature().toShortString();
        StringBuilder message = new StringBuilder();
        if (returningValue != null) {
            message.append(" Returns - ");
            if (returningValue instanceof Collection<?> returningCollect) {
                message.append("Collection size - ").append(returningCollect.size());
            } else if (returningValue instanceof byte[]) {
                message.append("File as byte[]");
            } else {
                message.append(returningValue);
            }
        }
        log.debug(ANSI_BLUE + "Service: " + methodName + " - end." + message + ANSI_RESET);
    }
}

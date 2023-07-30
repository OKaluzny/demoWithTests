package com.example.demowithtests.util.annotations;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.example.demowithtests.util.annotations.LogColorConstants.ANSI_BLUE;
import static com.example.demowithtests.util.annotations.LogColorConstants.ANSI_RESET;

@Log4j2
@Aspect
@Component
public class LoggingServiceClassesAspect {

    @Pointcut("execution(public * com.example.demowithtests.service.EmployeeServiceBean.*(..))")
    public void callAtMyServicesPublicMethods() {
    }

    @Before("callAtMyServicesPublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - start. Args count - {}" + ANSI_RESET, args.length);
        } else {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - start." + ANSI_RESET);
        }
    }

    @AfterReturning(value = "callAtMyServicesPublicMethods()", returning = "returningValue")
    public void logAfter(JoinPoint joinPoint, Object returningValue) {
        String methodName = joinPoint.getSignature().toShortString();
        Object outputValue;
        if (returningValue != null) {
            if (returningValue instanceof Collection) {
                outputValue = "Collection size - " + ((Collection<?>) returningValue).size();
            } else if (returningValue instanceof byte[]) {
                outputValue = "File as byte[]";
            } else {
                outputValue = returningValue;
            }
            log.debug(ANSI_BLUE + "Service: " + methodName + " - end. Returns - {}" + ANSI_RESET, outputValue);
        } else {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - end." + ANSI_RESET);
        }
    }
}

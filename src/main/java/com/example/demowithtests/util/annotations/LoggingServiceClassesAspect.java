package com.example.demowithtests.util.annotations;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
            log.debug("\u001B[34m" + "Service: " + methodName + " - start. Args count - {}" + "\u001B[0m", args.length);
        } else {
            log.debug("\u001B[34m" + "Service: " + methodName + " - start." + "\u001B[0m");
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
            log.debug("\u001B[34m" + "Service: " + methodName + " - end. Returns - {}" + "\u001B[0m", outputValue);
        } else {
            log.debug("\u001B[34m" + "Service: " + methodName + " - end." + "\u001B[0m");
        }
    }
}

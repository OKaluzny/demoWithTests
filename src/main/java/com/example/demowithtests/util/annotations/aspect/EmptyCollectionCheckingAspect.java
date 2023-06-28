package com.example.demowithtests.util.annotations.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

import static com.example.demowithtests.util.annotations.aspect.logger.LogColourConstant.*;

@Aspect
@Component
@Slf4j
public class EmptyCollectionCheckingAspect {

    @Pointcut("execution(public * com.example.demowithtests.service.EmployeeServiceBean.*(..))")
    public void callAtMyService() {
    }

    @AfterReturning(value = "callAtMyService()", returning = "returningValue")
    public void checkAfterReturningCollection(JoinPoint joinPoint, Object returningValue) throws EntityNotFoundException {
        if (returningValue == null ||
                (returningValue instanceof Collection<?> returningCollect && returningCollect.isEmpty())) {
            String methodName = joinPoint.getSignature().toShortString();
            log.debug(ANSI_YELLOW + methodName + " throws EntityNotFoundException"+ ANSI_RESET);
            throw new EntityNotFoundException();
        }
    }
}

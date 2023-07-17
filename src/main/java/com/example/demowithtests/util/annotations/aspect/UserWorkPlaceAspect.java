package com.example.demowithtests.util.annotations.aspect;

import com.example.demowithtests.domain.UsersWorkPlaces;
import com.example.demowithtests.service.UserWorkPlaceServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.demowithtests.util.annotations.aspect.logger.LogColourConstant.*;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserWorkPlaceAspect {

    private final UserWorkPlaceServiceBean userWorkPlaceServiceBean;

    @Value("${workplace.delay}")
    private String delay;

    @Pointcut("execution(public * com.example.demowithtests.service.UserWorkPlaceServiceBean.create(..))")
    public void callAtUserWorkPlaceCreateMethod() {
    }

    @AfterReturning(value = "callAtUserWorkPlaceCreateMethod()", returning = "returningValue")
    public void startTimerAfterReturning(JoinPoint joinPoint, Object returningValue) throws EntityNotFoundException {
        if (returningValue instanceof UsersWorkPlaces usersWorkPlace) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    userWorkPlaceServiceBean.deactivateWorkPlace(usersWorkPlace);
                }
            };
            Timer timer = new Timer("UserWorkPlaceTimer");

            timer.schedule(task, Long.parseLong(delay) * 1000L);

            String methodName = joinPoint.getSignature().toShortString();
            log.debug(ANSI_PURPLE + "Service: " + methodName + " - starts timer with delay - " + delay + " sec" + ANSI_RESET);
        }
    }
}

package com.example.demowithtests.util.annotations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.hibernate.stat.internal.StatisticsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SQLQueryCounter {
    private final SessionFactory sessionFactory;

    @Autowired
    public SQLQueryCounter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Pointcut("execution(* com.example.demowithtests.repository.EmployeeRepository.saveAll(..))")
    public void callAtMySaveMethods() {}

    @AfterReturning("callAtMySaveMethods()")
    public void afterSaveAll() {
        Statistics statistics = sessionFactory.getStatistics();
        long queryCount = statistics.getPrepareStatementCount();
        log.info("SQL queries: " + queryCount);
    }
}

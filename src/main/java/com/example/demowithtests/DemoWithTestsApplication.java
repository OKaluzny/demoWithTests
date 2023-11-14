package com.example.demowithtests;

import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.fillDataBase.LoaderServiceBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoWithTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWithTestsApplication.class, args);


    }
}

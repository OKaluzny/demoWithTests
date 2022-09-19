package com.example.demowithtests;

import com.example.demowithtests.repository.JpqlRepository;
import com.example.demowithtests.repository.Repository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JpqlRepositoryTests {

    @Autowired
    private Repository repository;
    @Autowired
    private JpqlRepository jpqlRepository;

}

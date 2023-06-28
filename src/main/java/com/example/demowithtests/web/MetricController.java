package com.example.demowithtests.web;

import com.example.demowithtests.util.EndpointCallingCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Artem Kovalov on 25.06.2023
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MetricController {

    @GetMapping("/stat")
    public Map<String, Integer> getStatistic() {
        return EndpointCallingCounter.getEndpointsStat();
    }
}

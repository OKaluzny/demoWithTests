package com.example.demowithtests.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Artem Kovalov on 25.06.2023
 */
@Component
public record EndpointCallingCounter() {

    private static final Map<String, Integer> endpointsStat = new HashMap<>();

    public static void increment(String endpoint) {
        Integer endpointCount = endpointsStat.get(endpoint);
        int count = endpointCount != null ? endpointCount : 0;
        endpointsStat.put(endpoint, ++count);
    }

    public static Map<String, Integer> getEndpointsStat() {
        return endpointsStat;
    }

}

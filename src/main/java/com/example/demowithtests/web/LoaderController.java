package com.example.demowithtests.web;

import com.example.demowithtests.service.fillDataBase.LoaderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class LoaderController {

    private final LoaderService loaderService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/fill")
    public String fillDataBase() {
        log.info("fillDataBase() LoaderController - start: ");
        loaderService.generateData();
        String count = "Amount clients: " + loaderService.count();
        log.info("fillDataBase() LoaderController - end: count = {}", count);
        return count;
    }
}

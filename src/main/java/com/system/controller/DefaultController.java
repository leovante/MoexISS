package com.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public ResponseEntity findUserByName() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}

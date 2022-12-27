package com.cbms.users.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome to Spring Security App", HttpStatus.OK);
    }

    @GetMapping("/testSecurity")
    public ResponseEntity<String> testSecurity(){
        return new ResponseEntity<>("Testing to Spring Security App", HttpStatus.OK);
    }

}

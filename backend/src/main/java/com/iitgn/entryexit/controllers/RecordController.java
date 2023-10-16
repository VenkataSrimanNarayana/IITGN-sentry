package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecordController {

    private final UserRepository userRepository;


    @GetMapping("/records")
    public ResponseEntity<String> getRecords(){
        return new ResponseEntity<>("Karthikeya", HttpStatus.OK);
    }


    @PostMapping("/records")
    public ResponseEntity<String> newRecord(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

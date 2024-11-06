package com.mycalendar.controller;

import com.mycalendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
/*
    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(service.findUserById(id), HttpStatus.OK);
    }*/
}

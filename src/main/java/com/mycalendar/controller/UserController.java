package com.mycalendar.controller;

import com.mycalendar.model.dto.UserResponseDto;
import com.mycalendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(service.findUserById(id), HttpStatus.OK);
    }
}

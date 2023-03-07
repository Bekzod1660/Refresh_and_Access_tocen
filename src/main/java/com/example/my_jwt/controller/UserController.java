package com.example.my_jwt.controller;

import com.example.my_jwt.dto.UserDto;
import com.example.my_jwt.entity.User;
import com.example.my_jwt.repository.UserRepository;
import com.example.my_jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository authService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ')")
    public List<User> getUserList() {
        return authService.findAll();
    }


}

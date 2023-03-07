package com.example.my_jwt.controller;

import com.example.my_jwt.dto.ApiResponse;
import com.example.my_jwt.dto.UserDto;
import com.example.my_jwt.dto.UserRDto;
import com.example.my_jwt.entity.User;
import com.example.my_jwt.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public User register(@RequestBody UserDto userRegisterDto){

        return authService.add(userRegisterDto);
    }
    @PostMapping("login")
    public ApiResponse login(@RequestBody UserRDto usernamePasswordRequestDto) throws JsonProcessingException {
        return authService.login(usernamePasswordRequestDto);
    }

    @PostMapping("kdjnckdnskjdnskjc")

    public ApiResponse refreshToken(
            @RequestBody Map<String,String>stringMap
            ) throws JsonProcessingException {
        return authService.getAccessToken(stringMap.get("refresh_token"));
    }


}

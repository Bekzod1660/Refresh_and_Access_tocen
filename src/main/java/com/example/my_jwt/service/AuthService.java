package com.example.my_jwt.service;

import com.example.my_jwt.dto.ApiResponse;
import com.example.my_jwt.dto.UserDto;
import com.example.my_jwt.dto.UserRDto;
import com.example.my_jwt.entity.User;
import com.example.my_jwt.repository.UserRepository;
import com.example.my_jwt.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public User add(UserDto userDto) {
        User user=new User(
                userDto.getName(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getUsername(),
                userDto.getPermissionEnumList(),
                userDto.getRoleEnumList()
                );
        return userRepository.save(user);
    }

    public ApiResponse login(UserRDto userDto) throws UsernameNotFoundException, JsonProcessingException {
       User user= userRepository.findByUsername(userDto.getUsername());
        if (user == null ) {
            throw new UsernameNotFoundException("username or password is incorrect");
        }
        if (!passwordEncoder.matches(userDto.getPassword(),  user.getPassword())){
            throw new UsernameNotFoundException("username or password is incorrect");

        }
       // return "Bearer  " + JwtUtils.token(userDB.get());
        return new ApiResponse(
                0,
                "success",
                Map.of("access_token",jwtUtils.generateAccessToken(user),
                        "refresh_token",jwtUtils.generateRefreshToken(user))
        );
    }

    public ApiResponse getAccessToken(String refreshToken) throws JsonProcessingException {
        Claims accessClaims = jwtUtils.isRefreshValid(refreshToken);
        if (accessClaims!=null){
            String subject = accessClaims.getSubject();
            User repository = userRepository.findByUsername(subject);
            if (repository!=null){
                return new ApiResponse(
                        0,
                        "success",
                        Map.of("access_token",jwtUtils.generateAccessToken(repository))
                );

            }
        }
        return null;
    }

}

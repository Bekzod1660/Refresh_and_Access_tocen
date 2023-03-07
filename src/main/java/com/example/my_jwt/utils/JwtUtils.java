package com.example.my_jwt.utils;


import com.example.my_jwt.entity.User;
import com.example.my_jwt.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwtAccessSecretKey}")
    private String jwtAccessSecretKey;
    @Value("${jwtRefreshSecretKey}")
    private String jwtRefreshSecretKey;
    @Value("${expirationAccessTime}")
    private Long expirationAccessTime;

    @Value("${expirationRefreshTime}")
    private Long expirationRefreshTime;
    private final RedisService redisService;
    public  String generateAccessToken(
            User userDetails
    ) throws JsonProcessingException {
        String redisKey = redisService.addUserRedis(userDetails);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecretKey)
                .setSubject(redisKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() +expirationAccessTime))
                //.claim("authorities", getAuthorities(userDetails))
                .compact();
    }

    public  String generateRefreshToken(
            User userDetails
    ) throws JsonProcessingException {
      //  String redisKey = redisService.addUserRedis(userDetails);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecretKey)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() +expirationRefreshTime))
                //.claim("authorities", getAuthorities(userDetails))
                .compact();
    }

//    private  List<String> getAuthorities(User user) {
//        List<String> role = new ArrayList<>();
//        user.getPermissionEnumList().forEach((permissionEnum -> {
//            role.add(permissionEnum.name());
//        }));
//        user.getRoleEnumsL().forEach((roleEnum -> {
//            role.add("ROLE_" + roleEnum);
//        }));
//        return role;
//    }

    public  Claims isAccessValid(String token) {return getAccessClaims(token);}
    public  Claims isRefreshValid(String token) {return getRefreshClaims(token);}

    public  List<String> getAuthorities(Claims claims) {
        return (List<String>) claims.get("authorities");
    }

    public  synchronized Claims getAccessClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtAccessSecretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public  synchronized Claims getRefreshClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtRefreshSecretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

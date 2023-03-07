package com.example.my_jwt.service;

import com.example.my_jwt.entity.User;
import com.example.my_jwt.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate, UserRepository userRepository) {
        this.redisTemplate = redisTemplate;

        this.userRepository = userRepository;
    }

    public String addUserRedis(User user) throws JsonProcessingException {

        String userStr = new ObjectMapper().writeValueAsString(user);
        if (user.getRedisKey()!=null){
            redisTemplate.opsForValue().setIfPresent(user.getRedisKey(),userStr);
            return user.getRedisKey();
        }
        String redisKey = UUID.randomUUID().toString();
        user.setRedisKey(redisKey);
        User user1 = userRepository.save(user);
        String userMongoDB = new ObjectMapper().writeValueAsString(user1);
      redisTemplate.opsForValue().set(redisKey,userMongoDB);
      return user.getRedisKey();
    }
    public User getUserFromRedis(final String redisKey) throws JsonProcessingException {
        String s = redisTemplate.opsForValue().get(redisKey);
        return new ObjectMapper().readValue(s, User.class);
    }
}

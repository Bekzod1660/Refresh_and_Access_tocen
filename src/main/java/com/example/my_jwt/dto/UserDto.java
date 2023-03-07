package com.example.my_jwt.dto;

import com.example.my_jwt.entity.enume.PermissionEnum;
import com.example.my_jwt.entity.enume.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String name;
    private String password;
    private String username;
    @JsonProperty("permission")
    private List<PermissionEnum>permissionEnumList;
    @JsonProperty("role")
    private List<RoleEnum>roleEnumList;
}

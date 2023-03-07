package com.example.my_jwt.entity;

import com.example.my_jwt.entity.enume.PermissionEnum;
import com.example.my_jwt.entity.enume.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document
public class User implements UserDetails {
    private String name;
    private String password;
    private String username;
    private String redisKey;

    private List<PermissionEnum>permissionEnumList;
    private List<RoleEnum>roleEnumsL;

    public User(String name, String password, String username, List<PermissionEnum> permissionEnumList, List<RoleEnum> roleEnumsL) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.permissionEnumList = permissionEnumList;
        this.roleEnumsL = roleEnumsL;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        roleEnumsL.forEach((role)->{
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        });

        permissionEnumList.forEach((per)->{
            roles.add(new SimpleGrantedAuthority(per.name()));
        });
        return roles;
    }


    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.amigos.jwtdemo.service;

import com.amigos.jwtdemo.domain.AppUser;
import com.amigos.jwtdemo.domain.Role;

import java.util.List;

public interface UserService {
    AppUser SaveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();


}

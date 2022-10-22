package com.amigos.jwtdemo.service;

import com.amigos.jwtdemo.domain.AppUser;
import com.amigos.jwtdemo.domain.Role;
import com.amigos.jwtdemo.repo.RoleRepo;
import com.amigos.jwtdemo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor // used for dependency injection
@Transactional //all methods will be transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override//this method will add authorities for users
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user=userRepo.findByUsername(username);
        if(user == null){
            log.error("user not found");
            throw new UsernameNotFoundException("user not found");
        }else {
            log.info("user {} found in db",username);
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(),user.getPassword(),authorities);
    }
    @Override
    public AppUser SaveUser(AppUser user) {
        log.info("saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }



    @Override
    public Role saveRole(Role role) {
        log.info("savin new role {} to the db", role.getName());

        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to user {}", roleName, username);
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);

        user.getRoles().add(role); //no need to save the user after add role , because it's a transactional method..will save auto
    }

    @Override
    public AppUser getUser(String username) {

        log.info("fetching user    {}", username);
        return userRepo.findByUsername(username);
    }


    @Override
    public List<AppUser> getUsers() {
        log.info("fetching all users");

        return userRepo.findAll();
    }



}

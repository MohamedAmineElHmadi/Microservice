package com.amigos.jwtdemo.repo;

import com.amigos.jwtdemo.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface UserRepo extends JpaRepository<AppUser,Long> {

    AppUser findByUsername(String username);
}

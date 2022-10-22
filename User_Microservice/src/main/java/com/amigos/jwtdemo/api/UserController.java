package com.amigos.jwtdemo.api;

import com.amigos.jwtdemo.domain.AppUser;
import com.amigos.jwtdemo.domain.Role;
import com.amigos.jwtdemo.exception.RessourceNotFoundException;
import com.amigos.jwtdemo.repo.RoleRepo;
import com.amigos.jwtdemo.repo.UserRepo;
import com.amigos.jwtdemo.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping("/user/{id}")
    public AppUser getUserById(@PathVariable("id") Long id) throws  Exception{
        return userRepo.findById(id).orElseThrow(()->new RessourceNotFoundException(" Ce user  n'existe pas" +id));
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<AppUser> updateUser (@PathVariable(value = "id")Long Id,
                                                  @Validated @RequestBody AppUser roleDetails )
            throws RessourceNotFoundException {
        AppUser user=userRepo.findById(Id)
                .orElseThrow(()-> new RessourceNotFoundException("user not found for this id :" +Id));
        user.setName(roleDetails.getName());
        user.setUsername(roleDetails.getUsername());
        user.setPassword(roleDetails.getPassword());
        user.setRoles(roleDetails.getRoles());
        final AppUser updatedUser =userRepo.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/user/{id}")
    public Map<String,Boolean> deleteUser (@PathVariable(value="id")Long Id)
            throws  RessourceNotFoundException        {
        AppUser user=userRepo.findById(Id)
                .orElseThrow(()->new RessourceNotFoundException("User not found for this id:"+Id));
        userRepo.delete(user);
        Map<String,Boolean> response =new HashMap<>();
        response.put("User Has been deleted:",Boolean.TRUE);
        return response;
    }



    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        log.info("the URI :{}",uri);
        log.info(user.toString());
//ServletUriComponentsBuilder.fromCurrentContextPath() will return the localhost:8080/
        return ResponseEntity.created(uri).body(userService.SaveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleRepo.findAll();
    }
    @GetMapping("/role/{id}")
    public Role getRoleById(@PathVariable("id") Long id) throws  Exception{
        return roleRepo.findById(id).orElseThrow(()->new RessourceNotFoundException("Ce Role n'existe pas" +id));
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Role> updateRole (@PathVariable(value = "id")Long Id,
                                               @Validated @RequestBody Role roleDetails )
            throws RessourceNotFoundException {
        Role role=roleRepo.findById(Id)
                .orElseThrow(()-> new RessourceNotFoundException("Role not found for this id :" +Id));
        role.setName(roleDetails.getName());
        final Role updatedRole =roleRepo.save(role);
        return ResponseEntity.ok(updatedRole);
    }
    @DeleteMapping("/role/{id}")
    public Map<String,Boolean> deleteRole (@PathVariable(value="id")Long Id)
            throws  RessourceNotFoundException        {
        Role role=roleRepo.findById(Id)
                .orElseThrow(()->new RessourceNotFoundException("Role not found for this id:"+Id));
        roleRepo.delete(role);
        Map<String,Boolean> response =new HashMap<>();
        response.put("Role Has been deleted:",Boolean.TRUE);
        return response;
    }


    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))//expire after 10mins
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))//roles
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                log.error("error loging :{}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("refresh token is missing");
        }
    }


}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;

}

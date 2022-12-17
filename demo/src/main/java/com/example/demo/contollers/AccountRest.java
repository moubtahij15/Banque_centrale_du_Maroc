package com.example.demo.contollers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entities.Role;
import com.example.demo.entities.UserApp;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.AccountImplClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRest {
    final AccountImplClient accountImplClient;

    public AccountRest(AccountImplClient accountImplClient) {
        this.accountImplClient = accountImplClient;
    }

    @PostAuthorize("hasAnyAuthority('CLIENT','ADMIN')")
    @GetMapping(path = "/clients")
    List<UserApp> clients() {
        return accountImplClient.listUser();
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/clients")
    public UserApp saveClient(@RequestBody UserApp userApp) {
        return accountImplClient.addNewClient(userApp);
    }

    @GetMapping(path = "/roles")
    List<Role> roles() {
        return accountImplClient.listRole();
    }

    @PostMapping(path = "/roles")
    public Role saveRole(@RequestBody Role role) {
        return accountImplClient.addRole(role);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody String username, @RequestBody String roleName) {
        accountImplClient.addRoleToUser(username, roleName);
    }

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtRefreshToken = request.getHeader(JwtUtil.AUTH_HEADER);

        if (jwtRefreshToken != null && jwtRefreshToken.startsWith(JwtUtil.PREFIX)) {
            try {
                String jwt = jwtRefreshToken.substring(JwtUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();

                UserApp userApp = accountImplClient.loadUserByUsername(username);

                String jwtAccessToken = JWT.create()
                        .withSubject(userApp.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", userApp.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            } catch (Exception e) {
                response.setHeader("error-message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);

            }
        } else {
            throw new RuntimeException("Refresh token is required");
        }
    }

    @GetMapping(path = "/profile")
    UserApp profile(Principal principal) {
            return accountImplClient.loadUserByUsername(principal.getName());
    }
}


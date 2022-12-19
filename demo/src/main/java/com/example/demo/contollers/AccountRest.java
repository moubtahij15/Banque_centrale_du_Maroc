package com.example.demo.contollers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entities.Agent;
import com.example.demo.entities.Client;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.AccountImplAdmin;
import com.example.demo.services.AccountImplClient;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserDetailServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
public class AccountRest {
    final AccountImplClient accountImplClient;
    final AccountImplAdmin accountImplAdmin;


    public AccountRest(AccountImplClient accountImplClient, AccountImplAdmin accountImplAdmin) {
        this.accountImplClient = accountImplClient;
        this.accountImplAdmin = accountImplAdmin;
    }

    @PostAuthorize("hasAnyAuthority('CLIENT','AGENT')")
    @GetMapping(path = "/clients")
    List<Client> clients() {
        return accountImplClient.listUser();
    }

    @PostAuthorize("hasAuthority('AGENT')")
    @PostMapping(path = "/clients")
    public Client saveClient(@RequestBody Client client) {
        return accountImplClient.addNewUser(client);
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

                Client client = accountImplClient.loadUserByUsername(username);

                String jwtAccessToken = JWT.create()
                        .withSubject(client.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", client.getRole().toString())
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
    Object profile(Principal principal) {
        if (UserDetailServiceImp.role.equals("AGENT")) {
            return (Agent) accountImplAdmin.loadUserByUsername(principal.getName());

        } else if (UserDetailServiceImp.role.equals("CLIENT")) {
            return accountImplClient.loadUserByUsername(principal.getName());

        }
        return null;
    }
}


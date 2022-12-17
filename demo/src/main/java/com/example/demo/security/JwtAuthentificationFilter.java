package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entities.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    AuthenticationManager authenticationManager;

    public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        System.out.println(request.getRequestURL().toString());
        System.out.println("attemptAuthentication");
//        System.out.println("from");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(email + "----" + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_ACCESS_refresh))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);


        Map<String, String> idToken = new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefreshToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);

//        response.setHeader("Authorization", jwtAccessToken);


    }
}

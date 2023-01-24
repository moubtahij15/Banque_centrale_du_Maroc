package com.example.demo.security;


import com.example.demo.DTO.Logged;
import com.example.demo.repo.AgentRepository;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {
    final JwtEncoder jwtEncoder;
    private final AgentRepository agentRepository;

    public TokenGenerator(JwtEncoder jwtEncoder,
                          AgentRepository agentRepository) {
        this.jwtEncoder = jwtEncoder;
        this.agentRepository = agentRepository;
    }

    public Logged execute(String subject, boolean withRefreshToken, String scope, long EXPIRE_ACCESS_TOKEN, long EXPIRE_REFRESH_TOKEN) {
        Logged logged = new Logged();
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(EXPIRE_ACCESS_TOKEN, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope", scope)
                .build();
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        logged.setAccessToken(jwtAccessToken);
        if (withRefreshToken) {
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(EXPIRE_REFRESH_TOKEN, ChronoUnit.MINUTES))
                    .issuer("security-service")
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
            logged.setRefreshToken(jwtRefreshToken);
        }
        logged.setRole(scope);
        return logged;
    }
}

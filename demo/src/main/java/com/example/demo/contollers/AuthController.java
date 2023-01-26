package com.example.demo.contollers;


import com.example.demo.DTO.TokenRequest;
import com.example.demo.helpers.EmailDetails;
import com.example.demo.helpers.Info;
import com.example.demo.repo.AgentRepository;
import com.example.demo.repo.ClientRepository;
import com.example.demo.DTO.Logged;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.TokenGenerator;
import com.example.demo.security.UserDetailServiceImp;
import com.example.demo.services.EmailServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    private TokenGenerator tokenGenerator;
    private JwtDecoder jwtDecoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    final EmailServiceImpl emailService;

    final AgentRepository agentRepository;
    final ClientRepository clientRepository;


    public AuthController(TokenGenerator tokenGenerator, JwtDecoder jwtDecoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, EmailServiceImpl emailService,
                          AgentRepository agentRepository, ClientRepository clientRepository) {
        this.tokenGenerator = tokenGenerator;
        this.jwtDecoder = jwtDecoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<Object> jwtToken(@RequestBody TokenRequest tokenRequest) throws JsonProcessingException {
        String subject = null;
        String scope = null;
        Authentication authentication = null;
//        System.out.printf(String.valueOf(tokenRequest));
        UserDetailServiceImp.role = tokenRequest.getRole();

        Logged user = new Logged();
        Logged logged = new Logged();

        if (tokenRequest.getGrantType().equals("password")) {
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));

            } catch (Exception e) {
                return new ResponseEntity<>(Map.of("statut", "error",
                        "type", "bad credentials"
                        , "errorMessage", e.getMessage())
                        , HttpStatus.OK);

            }


            if (tokenRequest.getRole().equals("CLIENT")) {
                user.setClient(clientRepository.findByEmail(authentication.getName()));
                if (user.getClient().getEtat().equals(Info.Etat.EN_COURS.toString())) {
                    return new ResponseEntity<>(Map.of("statut", "error",
                            "type", "validation",
                            "id", user.getClient().getId()
                            , "errorMessage", "le compte pas encore valide  checker votre boite mail"), HttpStatus.OK);
                }
            } else {
                user.setAgent(agentRepository.findByUsername(authentication.getName()));

            }

            subject = authentication.getName();
            scope = authentication.getAuthorities()
                    .stream().map(aut -> aut.getAuthority())
                    .collect(Collectors.joining(""));


        } else if (tokenRequest.getGrantType().equals("refreshToken")) {
            if (tokenRequest.getRefreshToken() == null) {
                return new ResponseEntity<>(Map.of("errorMessage", "Refresh  Token is required"), HttpStatus.UNAUTHORIZED);
            }
            Jwt decodeJWT = null;
            try {
                decodeJWT = jwtDecoder.decode(tokenRequest.getRefreshToken());
            } catch (JwtException e) {
                return new ResponseEntity<>(Map.of("errorMessage", e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            subject = decodeJWT.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            scope = authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        }

        logged = tokenGenerator.execute(subject, tokenRequest.isWithRefreshToken(), scope, JwtUtil.EXPIRE_ACCESS_TOKEN, JwtUtil.EXPIRE_REFRESH_TOKEN);
        logged.setAgent(user.getAgent());
        logged.setClient(user.getClient());
        return ResponseEntity.ok(logged);

    }


    @GetMapping("send")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }


    @GetMapping(path = "/profilae")
    public ResponseEntity<String> myEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // check the user's roles and perform the appropriate logic
        System.out.println(authorities);
        return new ResponseEntity<>("Endpoint accessed", HttpStatus.OK);
    }

    @GetMapping(path = "/aa")
    public ResponseEntity<String> s() {

        return new ResponseEntity<>("Endpoint accessed", HttpStatus.OK);
    }

}

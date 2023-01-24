package com.example.demo.contollers;


import com.example.demo.entities.Client;
import com.example.demo.DTO.ClientRequest;
import com.example.demo.helpers.EmailDetails;
import com.example.demo.helpers.Info;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.TokenGenerator;
import com.example.demo.services.AccountImplClient;
import com.example.demo.services.EmailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(path = "client")
@RestController
public class ClientRest {
    final AccountImplClient accountImplClient;
    final EmailServiceImpl emailService;
    final EmailDetails emailDetails;
    final TokenGenerator tokenGenerator;
    final JwtDecoder jwtDecoder;

    public ClientRest(AccountImplClient accountImplClient, EmailServiceImpl emailService, EmailDetails emailDetails, TokenGenerator tokenGenerator, JwtDecoder jwtDecoder) {
        this.accountImplClient = accountImplClient;
        this.emailService = emailService;
        this.emailDetails = emailDetails;
        this.tokenGenerator = tokenGenerator;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> saveClient(@RequestBody ClientRequest clientRequest) {
        Client client = new Client();
        client.setAdresse(clientRequest.getAdresse());
        client.setCin(clientRequest.getCin());
        client.setEmail(clientRequest.getEmail());
        client.setNom(clientRequest.getNom());
        client.setPassword(clientRequest.getPassword());
        client.setPrenom(clientRequest.getPrenom());
        client.setTel(clientRequest.getTel());
        client.setRole(Info.role.CLIENT.toString());
        client.setEtat(Info.Etat.NON_VALIDER.toString());
        String status = null;
        String token = tokenGenerator.execute(client.getEmail(), false, "Recruteure", JwtUtil.EXPIRE_VERIFY_TOKEN, 0).getAccessToken();
        if (accountImplClient.addNewUser(client, clientRequest.getCompteName()) != null) {
            System.out.println(client.getEmail());
            try {
                String html = "<style>  { background-color: yellow; font-weight: bold; } a { color: blue; text-decoration: none; } </style>" +
                        "<h6>Dear User</h1>" +
                        "<p>Thank you for signing up for our service. In order to complete your registration, please click on the following link to verify your email address.</p>" +
                        "<a href=http://localhost:8080/client/verify/" + token + ">Click here</a></li>" +
                        "<p>If you did not sign up for our service, you can ignore this email.</p>" +
                        "<p>Sincerely</p>" +
                        "<p> Banque Centrale.</p>";
                emailDetails.setRecipient(client.getEmail());
                emailDetails.setSubject("Email Verification");
//                emailDetails.setMsgBody("Dear User,\nThank you for signing up for our service. In order to complete your registration, please click on the following link to verify your email address:\n
//                        + "\n\nIf you did not sign up for our service, you can ignore this email.\n\nSincerely,\n[Banque Centrale]" +
//                        "<button>sdsd<button/>"
//                );
                emailDetails.setMsgBody(html
                );
                status = emailService.sendSimpleMail(emailDetails);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
//

        Map<String, String> idToken = new HashMap<>();
        idToken.put("status", status);

        return new ResponseEntity<>(idToken, HttpStatus.OK);
    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/valider")
    public String ValiderClient(@RequestParam Long id) {
        return accountImplClient.validerClient(id).getCin();
    }

    @GetMapping("verify/{token}")
    public ResponseEntity<Map<String, String>> verifier(@PathVariable String token) {
        try {
            Jwt decodeJWT = jwtDecoder.decode(token);
            String subject = decodeJWT.getSubject();
            accountImplClient.verifyClient(subject);
            System.out.println(subject);
            return new ResponseEntity<>(Map.of("Success", "votre compte était bien validé"), HttpStatus.OK);

        } catch (JwtException e) {
            return new ResponseEntity<>(Map.of("errorMessage", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}



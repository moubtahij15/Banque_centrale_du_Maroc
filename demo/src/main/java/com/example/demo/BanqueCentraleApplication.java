package com.example.demo;

import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.services.AccountImplClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class BanqueCentraleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueCentraleApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner start(AccountImplClient accountImplClient) {
        return args -> {
            accountImplClient.addRole(new Role(null, "CLIENT"));
            accountImplClient.addRole(new Role(null, "ADMIN"));
            accountImplClient.addRole(new Role(null, "USER"));

            accountImplClient.addNewClient(new Client("otman@gmail.com", "123456", "WA123", "CASA", "12345", "hamza", "mb"));
            accountImplClient.addNewClient(new Client("otman1@gmail.com", "123456", "WA1223", "CASA", "12345", "hamza", "mb"));
            accountImplClient.addNewClient(new Client("otman2@gmail.com", "123456", "WA1223", "CASA", "12345", "hamza", "mb"));

            accountImplClient.addRoleToUser("otman@gmail.com", "CLIENT");
            accountImplClient.addRoleToUser("otman1@gmail.com", "ADMIN");
            accountImplClient.addRoleToUser("otman2@gmail.com", "USER");

        };
    }

}


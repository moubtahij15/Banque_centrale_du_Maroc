package com.example.demo;

import com.example.demo.entities.Client;
import com.example.demo.helpers.Info;
import com.example.demo.repo.TransactionRepository;
import com.example.demo.security.RsakeysConfig;
import com.example.demo.services.AccountImplAdmin;
import com.example.demo.services.AccountImplClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsakeysConfig.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class BanqueCentraleApplication {

    TransactionRepository transactionRepository;

    public static void main(String[] args) {

        SpringApplication.run(BanqueCentraleApplication.class, args);

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
    CommandLineRunner start(AccountImplClient accountImplClient, AccountImplAdmin accountImplAdmin, TransactionRepository transactionRepository) {

        return args -> {


                accountImplClient.addNewUser(new Client("otman@gmail.com", "123456", "WA123", "CASA", "12345", "hamza", "mb", "CLIENT"), Info.TypeCompte.STANDARD.toString());
                accountImplClient.addNewUser(new Client("otman1@gmail.com", "123456", "WA1223", "CASA", "12345", "hamza", "mb", "CLIENT"), Info.TypeCompte.STANDARD.toString());
                accountImplClient.addNewUser(new Client("otman2@gmail.com", "123456", "WA1223", "CASA", "12345", "hamza", "mb", "CLIENT"), Info.TypeCompte.STANDARD.toString());

//
//            accountImplAdmin.addNewUser(new Agent("otman4@gmail.com", "123456", "WA1223", "CASA", "AGENT"));
//            accountImplAdmin.addNewUser(new Agent("otman5@gmail.com", "123456", "WA1223", "CASA", "AGENT"));

//            accountImplClient.addRoleToUser("otman@gmail.com", "CLIENT");
//            accountImplClient.addRoleToUser("otman1@gmail.com", "ADMIN");
//            accountImplClient.addRoleToUser("otman2@gmail.com", "USER");

        };
    }

}


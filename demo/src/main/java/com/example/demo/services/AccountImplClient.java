package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.repo.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AccountImplClient implements AccountService<Client> {
    final ClientRepository clientRepository;
    final PasswordEncoder passwordEncoder;


    public AccountImplClient(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client addNewUser(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }



    @Override
    public Client loadUserByUsername(String username) {

        return clientRepository.findByEmail(username);
    }

    @Override
    public List<Client> listUser() {
        return clientRepository.findAll();
    }


}

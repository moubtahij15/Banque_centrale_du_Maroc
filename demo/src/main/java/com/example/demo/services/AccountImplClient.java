package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.Request;
import com.example.demo.helpers.EmailDetails;
import com.example.demo.helpers.Info;
import com.example.demo.repo.ClientRepository;
import com.example.demo.repo.RequestRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AccountImplClient implements AccountService<Client> {
    final ClientRepository clientRepository;
    final RequestRepository requestRepository;
    final PasswordEncoder passwordEncoder;
    final CompteService compteService;
    final EmailServiceImpl emailService;
    final EmailDetails emailDetails;


    public AccountImplClient(ClientRepository clientRepository, RequestRepository requestRepository, PasswordEncoder passwordEncoder, CompteService compteService, EmailServiceImpl emailService, EmailDetails emailDetails) {
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
        this.compteService = compteService;
        this.emailService = emailService;
        this.emailDetails = emailDetails;
    }

    @Override
    public Client addNewUser(Client client) {
        return null;
    }

    @Override
    public Client addNewUser(Client client, String compteType) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setEtat(Info.Etat.EN_COURS.toString());
        requestRepository.save(new Request(Info.Etat.EN_COURS.toString(), clientRepository.save(client).getId(), compteType));
        return client;
    }


    @Override
    public Client loadUserByUsername(String username) {

        return clientRepository.findByEmail(username);
    }

    @Override
    public List<Client> listUser() {
        return clientRepository.findAll();
    }


    public Client validerClient(long id) {
        Request request = requestRepository.findRequestByClient_id(id);
        requestRepository.ValiderRequest(Info.Etat.VALIDER.toString(), request.getId());
        compteService.createCompte(id, request.getType());
        clientRepository.valider(id);
        System.out.println(id);
        return clientRepository.findClientById(2);
    }

    public void verifyClient(String email) {
        clientRepository.validerClient(email);
    }

}

package com.example.demo.services;

import com.example.demo.entities.Agent;
import com.example.demo.entities.Client;
import com.example.demo.repo.AgentRepository;
import com.example.demo.repo.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AccountImplAdmin implements AccountService<Agent> {
    final ClientRepository clientRepository;
    final PasswordEncoder passwordEncoder;
    private final AgentRepository agentRepository;


    public AccountImplAdmin(ClientRepository clientRepository, PasswordEncoder passwordEncoder,
                            AgentRepository agentRepository) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.agentRepository = agentRepository;
    }


    @Override
    public Agent addNewUser(Agent agent) {
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        return agentRepository.save(agent);
    }

    @Override
    public Client addNewUser(Client client, String compteType) {
        return null;
    }

    @Override
    public Agent loadUserByUsername(String username) {
        return agentRepository.findAgentByUsername(username);
    }

    @Override
    public List<Agent> listUser() {
        return null;
    }


}

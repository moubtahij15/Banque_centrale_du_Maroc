package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.repo.ClientRepository;
import com.example.demo.repo.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AccountImplClient implements AccountService<Client> {
    final ClientRepository clientRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;


    public AccountImplClient(ClientRepository clientRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client addNewClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Client client = clientRepository.findByEmail(email);
        Role role = roleRepository.findByRoleName(roleName);
        client.getRoles().add(role);

    }

    @Override
    public Client loadUserByUsername(String username) {

        return clientRepository.findByEmail(username);
    }

    @Override
    public List<Client> listUser() {
        return clientRepository.findAll();
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();
    }
}

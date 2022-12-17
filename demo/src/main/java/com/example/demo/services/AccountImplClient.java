package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.entities.UserApp;
import com.example.demo.repo.ClientRepository;
import com.example.demo.repo.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AccountImplClient implements AccountService {
    final ClientRepository clientRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;


    public AccountImplClient(ClientRepository clientRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserApp addNewUser(UserApp userApp) {
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return clientRepository.save(userApp);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        UserApp userApp = clientRepository.findByEmail(email);
        Role role = roleRepository.findByRoleName(roleName);
        userApp.getRoles().add(role);

    }

    @Override
    public UserApp loadUserByUsername(String username) {

        return clientRepository.findByEmail(username);
    }

    @Override
    public List<UserApp> listUser() {
        return clientRepository.findAll();
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();
    }
}

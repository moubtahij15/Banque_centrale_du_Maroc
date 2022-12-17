package com.example.demo.services;

import com.example.demo.entities.Role;

import java.util.List;

public interface AccountService<T> {

    T addNewClient(T t);

    Role addRole(Role role);

    void addRoleToUser(String email, String roleName);

    T loadUserByUsername(String username);

    List<T> listUser();

    List<Role> listRole();

}

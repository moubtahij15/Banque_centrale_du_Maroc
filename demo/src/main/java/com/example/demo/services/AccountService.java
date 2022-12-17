package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.entities.UserApp;

import java.util.List;

public interface AccountService {

    UserApp addNewUser(UserApp userApp);

    Role addRole(Role role);

    void addRoleToUser(String email, String roleName);

    UserApp loadUserByUsername(String username);

    List<UserApp> listUser();

    List<Role> listRole();

}

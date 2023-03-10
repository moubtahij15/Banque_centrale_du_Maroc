package com.example.demo.services;

import com.example.demo.entities.Client;

import java.util.List;

public interface AccountService<T> {

    T addNewUser(T t);

    Client addNewUser(Client client, String compteType);

    T loadUserByUsername(String username);

    List<T> listUser();


}

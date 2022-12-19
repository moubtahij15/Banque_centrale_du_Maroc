package com.example.demo.services;

import com.example.demo.entities.Agent;
import com.example.demo.entities.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    AccountImplAdmin accountImplAdmin;
    AccountImplClient accountImplClient;

    public static String role="";

    public UserDetailServiceImp(AccountImplAdmin accountImplAdmin, AccountImplClient accountImplClient) {
        this.accountImplAdmin = accountImplAdmin;
        this.accountImplClient = accountImplClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("from loadUserByUsername");
        System.out.println("-------");
        System.out.println(username);
        System.out.println("-------");
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (role.equals("AGENT")) {
            Agent agent = (Agent) accountImplAdmin.loadUserByUsername(username);
            grantedAuthorities.add(new SimpleGrantedAuthority(agent.getRole()));
            return new User(agent.getUsername(), agent.getPassword(), grantedAuthorities);

        } else if (role.equals("CLIENT")) {
            Client client = (Client) accountImplClient.loadUserByUsername(username);
            grantedAuthorities.add(new SimpleGrantedAuthority(client.getRole()));
            return new User(client.getEmail(), client.getPassword(), grantedAuthorities);

        }
        return null;

    }




}

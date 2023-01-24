package com.example.demo.security;


import com.example.demo.entities.Agent;
import com.example.demo.entities.Client;
import com.example.demo.services.AccountImplAdmin;
import com.example.demo.services.AccountImplClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    AccountImplAdmin agentService;
    AccountImplClient recruteurService;
    public static String error = "";


    public UserDetailServiceImp(AccountImplAdmin agentService, AccountImplClient recruteurService) {
        this.agentService = agentService;
        this.recruteurService = recruteurService;
    }

    public static String role = "";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("from loadUserByUsername");
        System.out.println("-------");
        System.out.println(username);
        System.out.println("-------");
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (role.equals("AGENT")) {
            Agent agent = (Agent) agentService.loadUserByUsername(username);
            grantedAuthorities.add(new SimpleGrantedAuthority(agent.getRole()));
            return new User(agent.getUsername(), agent.getPassword(), grantedAuthorities);

        } else if (role.equals("CLIENT")) {
            Client client = (Client) recruteurService.loadUserByUsername(username);
            grantedAuthorities.add(new SimpleGrantedAuthority(client.getRole()));

            return new User(client.getEmail(), client.getPassword(), grantedAuthorities);

        }
        return null;

    }


}

package com.example.demo.services;

import com.example.demo.entities.UserApp;
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
     AccountService accountService;

    public UserDetailServiceImp(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("from loadUserByUsername");
        System.out.println("-------");
        System.out.println(username);
        System.out.println("-------");
        UserApp userApp = (UserApp) accountService.loadUserByUsername(username);

        System.out.println("-------");
        System.out.println(userApp.getPassword());
        System.out.println("-------");        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        userApp.getRoles().forEach(role -> {
            grantedAuthorities.add((new SimpleGrantedAuthority(role.getRoleName())));
        });
        return new User(userApp.getEmail(), userApp.getPassword(), grantedAuthorities);
    }
}

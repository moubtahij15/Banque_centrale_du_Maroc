package com.example.demo.repo;

import com.example.demo.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<UserApp, Long> {
    UserApp findByEmail(String email);
}

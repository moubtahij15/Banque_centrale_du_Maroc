package com.example.demo.repo;

import com.example.demo.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Long> {
    Compte findCompteById(long id);
}

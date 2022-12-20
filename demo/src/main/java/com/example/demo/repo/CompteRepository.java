package com.example.demo.repo;

import com.example.demo.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findCompteById(long id);

    Compte findCompteByRib(String rib);


//    @Query
//    void operationSolde();
}

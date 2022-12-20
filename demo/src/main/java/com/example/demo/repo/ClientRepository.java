package com.example.demo.repo;

import com.example.demo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
//    Client findById(long id);
    Client findClientById(long id);
    @Modifying
    @Query("update Client client set client.etat='VALIDE' where client.id=?1")
    void valider(Long id);
}

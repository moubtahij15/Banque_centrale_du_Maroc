package com.example.demo.repo;

import com.example.demo.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findCompteById(long id);

    Compte findCompteByRib(String rib);


    @Modifying
    @Query("update  Compte compte set compte.sold=:solde where compte.id=:id")
    void updateSolde(@Param("solde") Double solde,
                     @Param("id") Long id);
}

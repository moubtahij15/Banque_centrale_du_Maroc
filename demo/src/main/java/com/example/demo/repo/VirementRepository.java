package com.example.demo.repo;

import com.example.demo.entities.Request;
import com.example.demo.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public interface VirementRepository
        extends JpaRepository<Virement, Long> {
    Virement findVirementByTransactions_Id(long transactions_id);

    Virement findVirementById(long id);


    @Modifying
    @Query("update Virement  virement set virement.etat=:etat where  virement.id=:id")
    void validerVirement(@Param("etat") String etat, @Param("id") Long id);

}

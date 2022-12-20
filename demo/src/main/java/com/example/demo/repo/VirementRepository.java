package com.example.demo.repo;

import com.example.demo.entities.Request;
import com.example.demo.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VirementRepository
        extends JpaRepository<Virement, Long> {
    Virement findVirementByTransactions_Id(long transactions_id);
    Virement findVirementById(long id);

}

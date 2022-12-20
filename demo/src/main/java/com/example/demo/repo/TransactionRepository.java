package com.example.demo.repo;

import com.example.demo.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE  t.idCompte=:compte and year(t.created)=:year and t.type=:type")
    Collection<Transaction> findTransactionByYear( @Param ("compte") long compte , @Param("year") int year,@Param("type")String type );
    @Query("SELECT t FROM Transaction t WHERE  t.idCompte=:compte and day(t.created)=20 and t.type=:type")
    Collection<Transaction> findTransactionByDay( @Param ("compte") long compte,@Param ("type") String type );

    //    Transaction  findTransactionByCreated_Year(int created_year) ;




//    ArrayList<Transaction> findAllDate(Date created);
//    ArrayList<Transaction> findAllByCreated_Year(Date created);

}

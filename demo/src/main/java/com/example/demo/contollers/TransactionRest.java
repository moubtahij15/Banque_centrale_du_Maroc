package com.example.demo.contollers;



import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.services.TransactionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping(path="api/client/")
public class TransactionRest {

    private  TransactionService transactionService;


    public TransactionRest(TransactionService transactionService ){
        this.transactionService=transactionService;

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/achat")
    public void achat(  @RequestParam String dateTransaction,
                        @RequestParam double montant,
                        @RequestParam long idCompte
                      ){

        Transaction transaction = new Transaction();

        transaction.setMontant(montant);
        transaction.setIdCompte(idCompte);
        transaction.setType("Achat");

        transactionService.achat(transaction);

    }



}

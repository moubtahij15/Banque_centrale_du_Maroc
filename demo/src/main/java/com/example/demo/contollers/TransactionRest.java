package com.example.demo.contollers;


import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.helpers.ClientVirement;
import com.example.demo.services.TransactionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping(path = "api/client/")
public class TransactionRest {

    private TransactionService transactionService;


    public TransactionRest(TransactionService transactionService) {
        this.transactionService = transactionService;

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/achat")
    public void achat(@RequestBody Transaction transaction1) {

//        Transaction transaction = new Transaction();
//
//        transaction.setMontant(transaction1.getMontant());
//        transaction.setIdCompte(transaction1.getIdCompte());
//        transaction.setType("Achat");
        System.out.println(transaction1.getDotation());
        transactionService.achat(transaction1);

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/virement")
    public void virement(@RequestBody ClientVirement clientVirement) {

//
//        transactionService.achat(transaction1);

    }


}

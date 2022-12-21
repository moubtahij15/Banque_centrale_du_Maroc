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
    public void achat(
            @RequestParam double montant,
            @RequestParam long idCompte,
            @RequestParam String dotation
    ) {

        Transaction transaction = new Transaction();

        transaction.setMontant(montant);
        transaction.setIdCompte(idCompte);
        transaction.setDotation(dotation);
        transaction.setType("Achat");
        transactionService.achat(transaction);

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path="/depot")
    public void achat(
            @RequestParam double montant,
            @RequestParam long idCompte
    ){
        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setType("depot");
        transaction.setIdCompte(idCompte);
        transactionService.depot(transaction);

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/virement")
    public void virement(@RequestBody ClientVirement clientVirement) {

//
//        transactionService.achat(transaction1);

    }


}

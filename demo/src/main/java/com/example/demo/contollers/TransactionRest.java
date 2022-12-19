package com.example.demo.contollers;



import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.services.TransactionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/client/")
public class TransactionRest {

    private  TransactionService transactionService;


    public TransactionRest(TransactionService transactionService ){
        this.transactionService=transactionService;

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/achat")
    public void achat(@RequestBody Transaction transaction ){
//        System.out.println(dateTransaction+" "+type+" "+email);
        System.out.println(transaction);
//
//        transactionService.achat(transaction);

    }



}

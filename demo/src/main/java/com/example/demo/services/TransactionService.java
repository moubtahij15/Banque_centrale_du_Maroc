package com.example.demo.services;


import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.repo.CompteRepository;
import com.example.demo.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
   private final CompteRepository compteRepository;

    public TransactionService(TransactionRepository transactionRepository,CompteRepository compteRepository){
        this.transactionRepository=transactionRepository;
        this.compteRepository=compteRepository;


    }
    public void achat(Transaction transaction) {

        Compte compte = compteRepository.findCompteById(1L);

        if (!compte.equals(null)){

            System.out.println(compte);


        }
        transactionRepository.save(transaction);
    }
}

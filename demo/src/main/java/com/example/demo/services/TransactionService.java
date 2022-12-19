package com.example.demo.services;


import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.helpers.Limit;
import com.example.demo.repo.CompteRepository;
import com.example.demo.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final Limit limit;
   private final CompteRepository compteRepository;

    public TransactionService(TransactionRepository transactionRepository,CompteRepository compteRepository,Limit limit){
        this.transactionRepository=transactionRepository;
        this.compteRepository=compteRepository;
        this.limit=limit;


    }
    public void achat(Transaction transaction) {

        Compte compte = compteRepository.findCompteById(transaction.getIdCompte());
        double solde = compte.getSold();
        double TransactionMontant = transaction.getMontant();
        String type= compte.getType();



        if (!compte.equals(null)){

            boolean positiveSolde = limit.checkSolde(solde,TransactionMontant);
            boolean positiveDaylimit = limit.checkDayLimit(TransactionMontant,type);
//            boolean

//            if ( solde>=TransactionMontant )
//            System.out.println(compte);
        }
        transactionRepository.save(transaction);
    }


}

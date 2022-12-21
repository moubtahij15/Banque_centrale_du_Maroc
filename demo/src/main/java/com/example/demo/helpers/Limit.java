package com.example.demo.helpers;


import com.example.demo.entities.Transaction;
import com.example.demo.repo.CompteRepository;
import com.example.demo.repo.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Limit {
    final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    public Limit(TransactionRepository transactionRepository,
                 CompteRepository compteRepository) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
    }


    public boolean checkSolde(double solde, double TransactionMontant) {
        if (solde >= TransactionMontant) {
            return true;
        }
        return false;
    }




//    public boolean checkYearLimit(double TransactionMontant,String type,double YearAmount,double AchatInetnationalYear,double AchatNationalYear){
//            double sum = TransactionMontant+YearAmount;
//
//
//
//    }


}

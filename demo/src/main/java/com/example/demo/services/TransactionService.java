package com.example.demo.services;


import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.helpers.Limit;
import com.example.demo.repo.CompteRepository;
import com.example.demo.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final Limit limit;
    private final CompteRepository compteRepository;

    public TransactionService(TransactionRepository transactionRepository, CompteRepository compteRepository, Limit limit) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
        this.limit = limit;


    }

    public void achat(Transaction transaction) {
        Compte compte = compteRepository.findCompteById(transaction.getIdCompte());
        double solde = compte.getSold();
        double TransactionMontant = transaction.getMontant();
        double YearAmount = 0;
        double AchatInetnationalYear = 0, AchatNationalYear = 0;
        double DayAmount = 0;
        boolean positiveSolde = true, positiveDaylimit = true, positiveYearlimit = true;
        String type = compte.getType();

        Collection<Transaction> transactionsOfTheYear = transactionRepository.findTransactionByYear(1, 2022, "Achat");
        Collection<Transaction> transactionsOfTheDay = transactionRepository.findTransactionByDay(1, "Achat");

        for (Transaction transactionYear : transactionsOfTheYear) {

            if (compte.getType().equals("pro") && transactionYear.getDotation().equals("international")) {

                AchatInetnationalYear += transactionYear.getMontant();

            } else if (compte.getType().equals("pro") && transactionYear.getDotation().equals("national")) {

                AchatNationalYear += transactionYear.getMontant();

            } else {
                for (Transaction transactionDay : transactionsOfTheDay
                ) {
                    DayAmount = transactionDay.getMontant();


                }
                YearAmount = transactionYear.getMontant();
            }


        }


        if (!compte.equals(null)) {

            positiveSolde = limit.checkSolde(solde, TransactionMontant);

            if (compte.getType().equals("standard")) {
                positiveDaylimit = limit.checkDayLimit(TransactionMontant, type, DayAmount);
            }

            positiveYearlimit = limit.checkYearLimit(TransactionMontant, type, YearAmount);


//            if ( solde>=TransactionMontant )
//            System.out.println(compte);
        }
        transactionRepository.save(transaction);
    }


}

package com.example.demo.services;


import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.helpers.Info;
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


    public void depot(Transaction transaction) {
        Compte compte = compteRepository.findCompteById(transaction.getIdCompte());
        compte.setSold(compte.getSold() + transaction.getMontant());
        compteRepository.save(compte);
        transactionRepository.save(transaction);

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

        Collection<Transaction> transactionsOfTheYear = transactionRepository.findTransactionByYear(transaction.getIdCompte(), 2022, "Achat");
        Collection<Transaction> transactionsOfTheDay = transactionRepository.findTransactionByDay(transaction.getIdCompte(), "Achat");
        transactionsOfTheYear.forEach(transaction1 -> System.out.println("test"));
        System.out.println("testststs");
        double sum = transactionsOfTheDay.stream().map(Transaction::getMontant).reduce(0000.0, Double::sum);
        for (Transaction transactionYear : transactionsOfTheYear) {
            if (compte.getType().equals("pro") && transactionYear.getDotation().equals("international")) {

                AchatInetnationalYear += transactionYear.getMontant();

            } else if (compte.getType().equals("pro") && transactionYear.getDotation().equals("national")) {

                AchatNationalYear += transactionYear.getMontant();

            } else {
                for (Transaction transactionDay : transactionsOfTheDay
                ) {
                    DayAmount += transactionDay.getMontant();


                }
                YearAmount += transactionYear.getMontant();
            }
        }

        System.out.println("AchatInetnationalYear " + AchatInetnationalYear);
        System.out.println("AchatNationalYear " + AchatNationalYear);
        System.out.println("DayAmount " + DayAmount);
        System.out.println("YearAmount " + YearAmount);

        if (!compte.equals(null)) {

            positiveSolde = limit.checkSolde(solde, TransactionMontant);

            if (compte.getType().equals("standard")) {

                double sumDay = TransactionMontant + DayAmount;
                double sumYear = TransactionMontant + YearAmount;
                if (sumDay > 5000) {
                    positiveDaylimit = false;
                }
                if (sumYear > 100000) {
                    positiveYearlimit = false;
                }
            } else {
                if (transaction.getDotation().equals("national")) {
                    double sumNational = AchatNationalYear + transaction.getMontant();
                    double sunInternational = AchatInetnationalYear + transaction.getMontant();
                    if (sumNational > 15000) {
                        positiveYearlimit = false;
                    } else if (sunInternational > 100000) {
                        positiveYearlimit = false;
                    }
                }
            }

            if (positiveSolde && positiveDaylimit && positiveYearlimit) {
                System.out.println("confirmed achat");
                System.out.println("sum is : " + sum);

                transactionRepository.save(transaction);
                compte.setSold(compte.getSold() - transaction.getMontant());
                compteRepository.save(compte);

            } else {
                System.out.println("you already reach the limit");
            }


        }
    }

    public boolean checkLimite(Transaction transaction) {
        System.out.println(transaction.getType());
        Collection<Transaction> transactionsOfTheYear = transactionRepository.findTransactionByYear(transaction.getIdCompte(), 2022, transaction.getType());
        Collection<Transaction> transactionsOfTheDay = transactionRepository.findTransactionByDay(transaction.getIdCompte(), transaction.getType());
        transaction.setCompteByIdCompte(compteRepository.findCompteById(transaction.getIdCompte()));
        double sumPerDayLocal = transactionsOfTheDay.stream().filter(transaction1 -> transaction1.getDotation().equals(Info.Dotation.LOCAL.toString())).map(Transaction::getMontant).reduce(0000.0, Double::sum);
        double sumPerYearLocal = transactionsOfTheYear.stream().filter(transaction1 -> transaction1.getDotation().equals(Info.Dotation.LOCAL.toString())).map(Transaction::getMontant).reduce(0000.0, Double::sum);
        double sumPerDayInternational = transactionsOfTheDay.stream().filter(transaction1 -> transaction1.getDotation().equals(Info.Dotation.INTERNATIONAL.toString())).map(Transaction::getMontant).reduce(0000.0, Double::sum);
        double sumPerYearInternational = transactionsOfTheYear.stream().filter(transaction1 -> transaction1.getDotation().equals(Info.Dotation.INTERNATIONAL.toString())).map(Transaction::getMontant).reduce(0000.0, Double::sum);
        double soldeUpdated = transaction.getCompteByIdCompte().getSold() - transaction.getMontant();
        boolean operationValide = false;
        if (transaction.getMontant() <= transaction.getCompteByIdCompte().getSold()) {
//            for achat operation
            if (transaction.getType().equals(Info.operation.ACHAT.toString())) {

                if (transaction.getCompteByIdCompte().getType().equals(Info.TypeCompte.STANDARD.toString())) {
                    if (sumPerDayLocal + transaction.getMontant() <= Info.standardPlafondAchatPerDay && sumPerYearLocal + transaction.getMontant() <= Info.standardPlafondAchatPerYear) {
                        operationValide = true;
                    }
                } else if (transaction.getCompteByIdCompte().getType().equals(Info.TypeCompte.PROFESSIONNEL.toString())) {
                    if (transaction.getDotation().equals(Info.Dotation.LOCAL.toString())) {
                        if (sumPerDayLocal + transaction.getMontant() <= Info.professionalPlafondAchatLocalPerDay && sumPerYearLocal + transaction.getMontant() <= Info.professionalPlafondAchatLocalPerYear) {
                            operationValide = true;
                        }
                    } else if (transaction.getDotation().equals(Info.Dotation.INTERNATIONAL.toString())) {
                        if (sumPerDayInternational + transaction.getMontant() <= Info.professionalPlafondAchatInternationalPerDay && sumPerYearInternational + transaction.getMontant() <= Info.professionalPlafondAchatInternationalPerYear) {
                            operationValide = true;
                        }
                    }
                }
            }
            //            for Retrait operation

            else if (transaction.getType().equals(Info.operation.RETRAIT.toString())) {
                if (transaction.getCompteByIdCompte().getType().equals(Info.TypeCompte.STANDARD.toString())) {
                    if (sumPerDayLocal + transaction.getMontant() <= Info.standardPlafondRetraitPerDay && sumPerYearLocal + transaction.getMontant() <= Info.standardPlafondRetraitPerYear) {
                        operationValide = true;
                    }
                } else if (transaction.getCompteByIdCompte().getType().equals(Info.TypeCompte.PROFESSIONNEL.toString())) {
                    if (sumPerDayLocal + transaction.getMontant() <= Info.professionalPlafondRetraitPerDay && sumPerYearLocal + transaction.getMontant() <= Info.professionalPlafondRetraitPerYear) {
                        operationValide = true;
                    }
                }
            }
        }

        if (operationValide) {
            System.out.println(transaction.getType());
            System.out.println(transaction.getIdCompte());
            compteRepository.updateSolde(soldeUpdated, transaction.getIdCompte());
            transactionRepository.save(transaction);
        }


        return operationValide;
    }
}

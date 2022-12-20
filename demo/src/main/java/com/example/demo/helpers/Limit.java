package com.example.demo.helpers;


import org.springframework.stereotype.Component;

@Component
public class Limit {

    public boolean checkSolde(double solde,double TransactionMontant){
        if(solde>=TransactionMontant){
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

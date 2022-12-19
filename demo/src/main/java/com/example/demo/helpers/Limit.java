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
    public boolean checkDayLimit(double TransactionMontant,String type){

        return false;
    }



}

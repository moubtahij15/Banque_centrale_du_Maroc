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

    //used only with standard account
    public boolean checkDayLimit(double TransactionMontant,String type,double DayAmount){
          double sum=  TransactionMontant+DayAmount;
          if(sum>5000){
                return false;
            }
            else{
                return true;
            }


    }
    public boolean checkYearLimit(double TransactionMontant,String type,double YearAmount){
            double sum = TransactionMontant+YearAmount;
        if(type.equals("standard")){

            if(sum>5000){
                return false;
            }
            else{
                return true;
            }

        }
        else{

        }
        return false;
    }



}

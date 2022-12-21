package com.example.demo.helpers;

import com.example.demo.entities.FactureInfo;
import com.example.demo.entities.Transaction;

public class ClientFacture {
    private Transaction transaction;
    private FactureInfo factureInfo;

    public ClientFacture(Transaction transaction, FactureInfo factureInfo) {
        this.transaction = transaction;
        this.factureInfo = factureInfo;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public FactureInfo getFactureInfo() {
        return factureInfo;
    }

    public void setFactureInfo(FactureInfo factureInfo) {
        this.factureInfo = factureInfo;
    }
}

package com.example.demo.DTO;

import com.example.demo.entities.Transaction;
import com.example.demo.entities.Virement;

public class ClientVirement {
    private Transaction transaction;
    private String ribDestinataire;

    public ClientVirement(Transaction transaction, String ribDestinataire) {
        this.transaction = transaction;
        this.ribDestinataire = ribDestinataire;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getRibDestinataire() {
        return ribDestinataire;
    }

    public void setRibDestinataire(String ribDestinataire) {
        this.ribDestinataire = ribDestinataire;
    }
}

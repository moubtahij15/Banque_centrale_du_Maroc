package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
public class Virement {
    private long id;
    private String ribDestinataire;
    private String etat;
    private Transaction transactions;

    @OneToOne
    public Transaction getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transactions) {
        this.transactions = transactions;
    }


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRibDestinataire(String ribDestinataire) {
        this.ribDestinataire = ribDestinataire;
    }


    @Basic
    @Column(name = "rib_destinataire", nullable = true)
    public String getRibDestinataire() {
        return ribDestinataire;
    }


    @Basic
    @Column(name = "etat", nullable = true, length = 255)
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    public Virement(String ribDestinataire, String etat) {
        this.ribDestinataire = ribDestinataire;
        this.etat = etat;
    }

    public Virement() {
    }
}

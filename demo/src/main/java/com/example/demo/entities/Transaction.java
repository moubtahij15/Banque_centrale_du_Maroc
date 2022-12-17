package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class Transaction {
    private long id;
    private Long idCompte;
    private Long dateTransaction;
    private String etat;
    private Double mentant;
    private String type;
    private Compte compteByIdCompte;
    private FactureInfo factureInfo;
    private Destinataire destinataires;

    @OneToOne
    public FactureInfo getFactureInfo() {
        return factureInfo;
    }

    public void setFactureInfo(FactureInfo factureInfo) {
        this.factureInfo = factureInfo;
    }

    @OneToOne
    public Destinataire getDestinataires() {
        return destinataires;
    }

    public void setDestinataires(Destinataire destinataires) {
        this.destinataires = destinataires;
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

    @Basic
    @Column(name = "id_compte", nullable = true)
    public Long getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Long idCompte) {
        this.idCompte = idCompte;
    }

    @Basic
    @Column(name = "date_transaction", nullable = true)
    public Long getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Long dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    @Basic
    @Column(name = "etat", nullable = true, length = 255)
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Basic
    @Column(name = "mentant", nullable = true, precision = 0)
    public Double getMentant() {
        return mentant;
    }

    public void setMentant(Double mentant) {
        this.mentant = mentant;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "id_compte", referencedColumnName = "id",insertable = false,updatable = false)
    public Compte getCompteByIdCompte() {
        return compteByIdCompte;
    }

    public void setCompteByIdCompte(Compte compteByIdCompte) {
        this.compteByIdCompte = compteByIdCompte;
    }
}

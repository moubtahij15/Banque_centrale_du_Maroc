package com.example.demo.entities;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Transaction {
    private long id;
    private long idCompte;
    private double montant;
    private String type;
    private Compte compteByIdCompte;
    private FactureInfo factureInfo;
    private Virement virements;
    private String dotation;
    private Date created;

    public String getDotation() {
        return dotation;
    }

    public void setDotation(String dotation) {
        this.dotation = dotation;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    private Date updated;

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
        this.updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }


    @OneToOne(mappedBy = "transaction")
    public FactureInfo getFactureInfo() {
        return factureInfo;
    }

    public void setFactureInfo(FactureInfo factureInfo) {
        this.factureInfo = factureInfo;
    }

    @OneToOne(mappedBy = "transactions")
    public Virement getVirements() {
        return virements;
    }

    public void setVirements(Virement virements) {
        this.virements = virements;
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
    public long getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(long idCompte) {
        this.idCompte = idCompte;
    }

    @Basic
    @Column(name = "montant", nullable = true, precision = 0)
    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
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
    @JoinColumn(name = "id_compte", referencedColumnName = "id", insertable = false, updatable = false)
    public Compte getCompteByIdCompte() {
        return compteByIdCompte;
    }

    public void setCompteByIdCompte(Compte compteByIdCompte) {
        this.compteByIdCompte = compteByIdCompte;
    }

    public Transaction(long idCompte, double montant, String type, String dotation) {
        this.idCompte = idCompte;
        this.montant = montant;
        this.type = type;
        this.dotation = dotation;
    }


    public Transaction() {
    }
}

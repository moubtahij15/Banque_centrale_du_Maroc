package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class FactureInfo {
    private long id;
    private Long type;
    private Long numeroContart;
    private Transaction transaction;

    @OneToOne
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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
    @Column(name = "type", nullable = true)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Basic
    @Column(name = "numero_contart", nullable = true)
    public Long getNumeroContart() {
        return numeroContart;
    }

    public void setNumeroContart(Long numeroContart) {
        this.numeroContart = numeroContart;
    }

    public FactureInfo(Long type, Long numeroContart) {
        this.type = type;
        this.numeroContart = numeroContart;
    }

    public FactureInfo() {
    }
}

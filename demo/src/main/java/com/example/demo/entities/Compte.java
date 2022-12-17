package com.example.demo.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Compte {
    private long id;
    private String rib;
    private Long idClient;
    private String type;
    private long sold;
    private Client clientByIdClient;
    private Collection<Transaction> transactionsById;

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
    @Column(name = "rib", nullable = true, length = 255)
    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    @Basic
    @Column(name = "id_client", nullable = true)
    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Basic
    @Column(name = "sold", nullable = false)
    public long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", insertable = false, updatable = false)
    public Client getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(Client clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @OneToMany(mappedBy = "compteByIdCompte")
    public Collection<Transaction> getTransactionsById() {
        return transactionsById;
    }

    public void setTransactionsById(Collection<Transaction> transactionsById) {
        this.transactionsById = transactionsById;
    }
}

package com.example.demo.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Compte {
    private long id;
    private String rib;
    private Long idClient;
    private String type;
    private double sold;
    private Client clientByIdClient;
    private Collection<Transaction> transactionsById;

    public Compte() {

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
    @ColumnDefault("0")
    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
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

    public Compte(String rib, Long idClient, String type, long sold) {
        this.rib = rib;
        this.idClient = idClient;
        this.type = type;
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", rib='" + rib + '\'' +
                ", idClient=" + idClient +
                ", type='" + type + '\'' +
                ", sold=" + sold +
                ", clientByIdClient=" + clientByIdClient +
                ", transactionsById=" + transactionsById +
                '}';
    }
}

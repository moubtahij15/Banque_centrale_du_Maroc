package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class Request {

    private Long id;

    private String Etat;
    private long client_id;

    private Client clientById;

    private String type;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "etat", nullable = true)
    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }
    @Basic
    @Column(name = "client_id", nullable = true)
    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Client getClientById() {
        return clientById;
    }

    public void setClientById(Client clientById) {
        this.clientById = clientById;
    }


    @Basic
    @Column(name = "type", nullable = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

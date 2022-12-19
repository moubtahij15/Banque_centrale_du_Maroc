package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String Etat;

    private Long compte_id;

    private String  type;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

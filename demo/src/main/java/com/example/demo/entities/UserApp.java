package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class UserApp {
    private long id;
    private String email;
    private String password;
    private String cin;
    private String adresse;

    private String urlCin;
    private String etat;
    private String tel;
    private String nom;
    private String prenom;
    private Collection<Compte> comptesById = new ArrayList<>();
    private Collection<Role> roles = new ArrayList<>();

    public UserApp() {
    }

    public UserApp(String email, String password, String cin, String adresse, String tel, String nom, String prenom) {
        this.email = email;
        this.password = password;
        this.cin = cin;
        this.adresse = adresse;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
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
    @Column(name = "password", nullable = true, length = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    @Basic
    @Column(name = "etat", nullable = true, length = 255)
    public String getEtat() {
        return etat;
    }

    @Basic
    @Column(name = "urlCin", nullable = true, length = 255)
    public String getUrlCin() {
        return urlCin;
    }

    public void setUrlCin(String urlCin) {
        this.urlCin = urlCin;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "cin", nullable = true, length = 255)
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    @Basic
    @Column(name = "adresse", nullable = true, length = 255)
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "tel", nullable = true, length = 255)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "nom", nullable = true, length = 255)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom", nullable = true, length = 255)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<Compte> getComptesById() {
        return comptesById;
    }

    public void setComptesById(Collection<Compte> comptesById) {
        this.comptesById = comptesById;
    }
}

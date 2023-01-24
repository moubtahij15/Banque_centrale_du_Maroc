package com.example.demo.DTO;

import com.example.demo.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
    private String email;
    private String password;
    private String cin;
    private String adresse;

    private String etat;
    private String tel;
    private String nom;
    private String prenom;
     private String compteName;

    public ClientRequest(String email, String password, String cin, String adresse, String tel, String nom, String prenom, String compteName) {
        this.email = email;
        this.password = password;
        this.cin = cin;
        this.adresse = adresse;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.compteName = compteName;
    }
}
package com.example.demo.helpers;

public interface Enum {
    enum Etat {VALIDE, NON_VALIDE, EN_COURS}

    enum TypeCompte {STANDARD, PROFESSIONNEL}

    enum role {AGENT, CLIENT}

    enum operation {ACHAT, RETRAIT, VIREMENT, FACTURE}
}

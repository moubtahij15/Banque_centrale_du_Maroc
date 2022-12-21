package com.example.demo.helpers;

public interface Info {
    enum Etat {VALIDE, NON_VALIDE, EN_COURS}

    enum TypeCompte {STANDARD, PROFESSIONNEL}

    enum role {AGENT, CLIENT}

    enum operation {ACHAT, RETRAIT, VIREMENT, FACTURE}

    enum Dotation {INTERNATIONAL, LOCAL}

    final String ss = "ez";

    final Double standardPlafondRetraitPerDay = 5000.0, standardPlafondRetraitPerYear = 100000.0;
    final Double standardPlafondAchatPerDay = 5000.0, standardPlafondAchatPerYear = 100000.0;

    final Double professionalPlafondRetraitPerDay = 10000.0, professionalPlafondRetraitPerYear = 200000.0;
    final Double professionalPlafondAchatLocalPerDay = 15000.0, professionalPlafondAchatLocalPerYear = 15000.0;
    final Double professionalPlafondAchatInternationalPerDay = 100000.0, professionalPlafondAchatInternationalPerYear = 100000.0;


}

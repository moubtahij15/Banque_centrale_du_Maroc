package com.example.demo.services;

import com.example.demo.entities.Compte;
import com.example.demo.repo.CompteRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;


@Service
public class CompteService {
    final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    String generateRib() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    Compte createCompte(long id_client, String type) {
        return compteRepository.save(new Compte(generateRib(), id_client, type, 0));

    }

}

package com.example.demo.services;

import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.entities.Virement;
import com.example.demo.helpers.Enum;
import com.example.demo.repo.CompteRepository;
import com.example.demo.repo.TransactionRepository;
import com.example.demo.repo.VirementRepository;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class CompteService {
    final CompteRepository compteRepository;
    final VirementRepository virementRepository;
    final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public CompteService(CompteRepository compteRepository, VirementRepository virementRepository, TransactionService transactionService,
                         TransactionRepository transactionRepository) {
        this.compteRepository = compteRepository;
        this.virementRepository = virementRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    String generateRib() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    void createCompte(long id_client, String type) {
        compteRepository.save(new Compte(generateRib(), id_client, type, 0));
    }

    boolean verser(Transaction transaction, String ribDestinataire) {

        if ((transaction.getCompteByIdCompte().getSold() >= transaction.getMontant()) && (compteRepository.findCompteByRib(ribDestinataire) != null)) {
            transactionRepository.save(transaction);
            virementRepository.save(new Virement(ribDestinataire, Enum.Etat.EN_COURS.toString()));

            return true;
        }
        return false;

    }

    boolean validerVirement(Long id_virement) {
        Compte emetteur = virementRepository.findVirementById(id_virement).getTransactions().getCompteByIdCompte();
//        Compte destinataire =
        return false;
    }


}

package com.example.demo.services;

import com.example.demo.entities.Compte;
import com.example.demo.entities.Transaction;
import com.example.demo.entities.Virement;
import com.example.demo.helpers.Info;
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

    public void createCompte(long id_client, String type) {
        compteRepository.save(new Compte(generateRib(), id_client, type, 0));
    }


    public String verser(Transaction transaction, String ribDestinataire) {
        Compte emetteur = compteRepository.findCompteById(transaction.getIdCompte());
        Compte destinataire = compteRepository.findCompteByRib(ribDestinataire);

        if ((emetteur.getSold() >= transaction.getMontant()) && (destinataire != null)) {
            transaction.setType(Info.operation.VIREMENT.toString());
            transactionRepository.save(transaction);
            Virement virement = new Virement(ribDestinataire, Info.Etat.EN_COURS.toString());
            virement.setTransactions(transaction);
            virementRepository.save(virement);
            compteRepository.updateSolde((emetteur.getSold() - transaction.getMontant()), transaction.getIdCompte());
            return "Bien Verser en attente la verification";
        }
        return "Votre Solde o rib incorrect";

    }

    public String validerVirement(Long id_virement) {
        Virement currentVirement = virementRepository.findVirementById(id_virement);
        Transaction currentTransaction = currentVirement.getTransactions();
        Compte destinataire = compteRepository.findCompteByRib(currentVirement.getRibDestinataire());
        virementRepository.validerVirement(Info.Etat.VALIDER.toString(), id_virement);
        compteRepository.updateSolde((destinataire.getSold() + currentTransaction.getMontant()), destinataire.getId());
        return "valide";
    }


}

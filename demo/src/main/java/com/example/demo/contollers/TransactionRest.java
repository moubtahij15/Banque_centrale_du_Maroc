package com.example.demo.contollers;


import com.example.demo.entities.Transaction;
import com.example.demo.DTO.ClientFacture;
import com.example.demo.DTO.ClientVirement;
import com.example.demo.helpers.Info;
import com.example.demo.repo.RequestRepository;
import com.example.demo.services.CompteService;
import com.example.demo.services.FactureService;
import com.example.demo.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/client/")
public class TransactionRest {

    private TransactionService transactionService;
    final CompteService compteService;

    final FactureService factureService;
    private final RequestRepository requestRepository;


    public TransactionRest(TransactionService transactionService, CompteService compteService,
                           FactureService factureService, RequestRepository requestRepository) {
        this.transactionService = transactionService;

        this.compteService = compteService;
        this.factureService = factureService;
        this.requestRepository = requestRepository;
    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/achat")
    public void achat(
            @RequestParam double montant,
            @RequestParam long idCompte,
            @RequestParam String dotation
    ) {

        Transaction transaction = new Transaction();

        transaction.setMontant(montant);
        transaction.setIdCompte(idCompte);
        transaction.setDotation(dotation);
        transaction.setType(Info.operation.ACHAT.toString());
        transactionService.checkLimite(transaction);

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/depot")
    public void achat(
            @RequestParam double montant,
            @RequestParam long idCompte
    ) {
        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setType("depot");
        transaction.setIdCompte(idCompte);
        transactionService.depot(transaction);

    }

    //    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/virement")
    public ResponseEntity<Object> virement(@RequestBody ClientVirement clientVirement) {
//        return compteService.verser(clientVirement.getTransaction(), clientVirement.getRibDestinataire());
        if (compteService.validerVirement(Long.valueOf(compteService.verser(clientVirement.getTransaction(), clientVirement.getRibDestinataire())))) {
            return new ResponseEntity<>(Map.of("status", "ok"), HttpStatus.CREATED);

        }
        return new ResponseEntity<>(Map.of("status", "not ok"), HttpStatus.FORBIDDEN);


    }

    //    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/virement/verifier")
    public boolean virement(@RequestParam Long id_virement) {
        return compteService.validerVirement(id_virement);

    }

    //    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/retrait")
    public ResponseEntity<Object> retrait(@RequestBody Transaction transaction) {

        transaction.setType(Info.operation.RETRAIT.toString());
        transaction.setDotation(Info.Dotation.LOCAL.toString());
        System.out.println(transaction.getIdCompte());
        System.out.println(transaction.getMontant());
        if (transactionService.checkLimite(transaction)) {
            return new ResponseEntity<>(Map.of("status", "ok"), HttpStatus.CREATED);
        } else {

            System.out.println("non Passer");
            return new ResponseEntity<>(Map.of("status", " not ok"), HttpStatus.CREATED);

        }


    }


    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/facture")
    public void facturePayment(@RequestBody ClientFacture clientFacture) {
//        clientFacture.getTransaction().setType(Info.operation.ACHAT.toString());
//        clientFacture.getTransaction().setDotation(Info.Dotation.LOCAL.toString());
//
        System.out.println(clientFacture.getFactureInfo().getType());
        System.out.println(clientFacture.getTransaction().getIdCompte());
//
//        if (transactionService.checkLimite(clientFacture.getTransaction())) {
//            System.out.println(clientFacture.getTransaction().getId());
//            clientFacture.getFactureInfo().setTransaction(clientFacture.getTransaction());
//            factureService.save(clientFacture.getFactureInfo());
//
//            System.out.println("Bien Retrait");
//        } else {
//            System.out.println("non Passer");
//
//        }

    }


}

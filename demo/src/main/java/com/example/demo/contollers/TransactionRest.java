package com.example.demo.contollers;


import com.example.demo.entities.Transaction;
import com.example.demo.helpers.ClientVirement;
import com.example.demo.repo.RequestRepository;
import com.example.demo.services.CompteService;
import com.example.demo.services.TransactionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/client/")
public class TransactionRest {

    private TransactionService transactionService;
    final CompteService compteService;
    private final RequestRepository requestRepository;


    public TransactionRest(TransactionService transactionService, CompteService compteService,
                           RequestRepository requestRepository) {
        this.transactionService = transactionService;

        this.compteService = compteService;
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
        transaction.setType("Achat");

        transactionService.achat(transaction);

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/virement")
    public String virement(@RequestBody ClientVirement clientVirement) {
        return compteService.verser(clientVirement.getTransaction(), clientVirement.getRibDestinataire());

    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/virement/verifier")
    public String virement(@RequestParam Long id_virement) {
        return compteService.validerVirement(id_virement);

    }


}

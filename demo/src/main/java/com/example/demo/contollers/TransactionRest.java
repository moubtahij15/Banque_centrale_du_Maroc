package com.example.demo.contollers;


import com.example.demo.entities.Transaction;
import com.example.demo.DTO.ClientFacture;
import com.example.demo.DTO.ClientVirement;
import com.example.demo.helpers.Info;
import com.example.demo.repo.RequestRepository;
import com.example.demo.services.CompteService;
import com.example.demo.services.FactureService;
import com.example.demo.services.TransactionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(path="/depot")
    public void achat(
            @RequestParam double montant,
            @RequestParam long idCompte
    ){
        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setType("depot");
        transaction.setIdCompte(idCompte);
        transactionService.depot(transaction);

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

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/retrait")
    public void retrait(@RequestBody Transaction transaction) {
        transaction.setType(Info.operation.RETRAIT.toString());
        transaction.setDotation(Info.Dotation.LOCAL.toString());

        if (transactionService.checkLimite(transaction)) {
            System.out.println("Bien Retrait");
        } else {
            System.out.println("non Passer");

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

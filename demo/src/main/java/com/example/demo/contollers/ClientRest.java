package com.example.demo.contollers;


import com.example.demo.entities.Client;
import com.example.demo.helpers.ClientRequest;
import com.example.demo.services.AccountImplClient;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "client")
@RestController
public class ClientRest {

    final AccountImplClient accountImplClient;

    public ClientRest(AccountImplClient accountImplClient) {
        this.accountImplClient = accountImplClient;
    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/add")
    public Client saveClient(@RequestBody ClientRequest clientRequest) {
        return accountImplClient.addNewUser(clientRequest.getClient(), clientRequest.getCompteName());
    }

    @PostAuthorize("hasAuthority('CLIENT')")
    @PostMapping(path = "/valider")
    public String ValiderClient(@RequestParam Long id) {
        return accountImplClient.validerClient(id).getCin();
    }

}



package com.example.demo.helpers;

import com.example.demo.entities.Client;

public class ClientRequest {
    Client client;
    String compteName;

    public ClientRequest(Client client, String compteName) {
        this.client = client;
        this.compteName = compteName;
    }

    public String getCompteName() {
        return compteName;
    }

    public void setCompteName(String compteName) {
        this.compteName = compteName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
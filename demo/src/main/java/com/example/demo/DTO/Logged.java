package com.example.demo.DTO;


import com.example.demo.entities.Agent;
import com.example.demo.entities.Client;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Logged {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Client client;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Agent agent;
    String accessToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String refreshToken;
    String role;
}

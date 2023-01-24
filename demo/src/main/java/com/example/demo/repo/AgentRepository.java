package com.example.demo.repo;

import com.example.demo.entities.Agent;
import com.example.demo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findAgentByUsername(String username);

    Agent findByUsername(String username);
}

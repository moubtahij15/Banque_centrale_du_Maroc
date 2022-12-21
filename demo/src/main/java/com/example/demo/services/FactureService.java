package com.example.demo.services;


import com.example.demo.entities.FactureInfo;
import com.example.demo.repo.FactureRepository;
import org.springframework.stereotype.Service;

@Service
public class FactureService {
    final FactureRepository factureRepository;

    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    public FactureInfo save(FactureInfo factureInfo) {
        return factureRepository.save(factureInfo);
    }
}

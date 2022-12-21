package com.example.demo.repo;

import com.example.demo.entities.FactureInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<FactureInfo, Long> {
}

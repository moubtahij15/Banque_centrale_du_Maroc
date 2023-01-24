package com.example.demo.repo;

import com.example.demo.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RequestRepository
        extends JpaRepository<Request, Long> {

    @Modifying
    @Query("update Request rq set rq.etat=:etat where rq.id=:id")
    void ValiderRequest(@Param("etat") String etat, @Param("id") long id);

    @Query("select request from Request request where request.client_id=:id")
    Request findRequestByClient_id(@Param("id") long id);


}

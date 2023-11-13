package com.petproject.nplusone;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends JpaRepository<Parent, Integer> {

    @Query("SELECT DISTINCT p FROM Parent p JOIN FETCH p.children")
    List<Parent> getParentsWithoutN1();

//    @EntityGraph(attributePaths = {"children"})
    List<Parent> findAll();
}

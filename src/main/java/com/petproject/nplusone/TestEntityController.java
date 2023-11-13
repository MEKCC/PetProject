package com.petproject.nplusone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class TestEntityController {

    @Autowired
    private TestRepo testRepo;

    @GetMapping("/hibernate")
    public List<Parent> getAllParents() {
//        return testRepo.getParentsWithoutN1();
        return testRepo.findAll();
    }
}

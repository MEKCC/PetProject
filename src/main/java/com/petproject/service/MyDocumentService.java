package com.petproject.service;

import com.petproject.model.MyDocument;
import com.petproject.repo.MyDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyDocumentService {

    @Autowired
    private MyDocumentRepository repository;

    public List<MyDocument> getMyDocumentsFromElasticSearch() {
        final Iterable<MyDocument> documentsIterable = repository.findAll();
        final List<MyDocument> documentsList = new ArrayList<>();
        documentsIterable.forEach(documentsList::add);
        return documentsList;
    }

    public void indexDocuments(final List<MyDocument> myDocuments) {
        repository.saveAll(myDocuments);
    }
}


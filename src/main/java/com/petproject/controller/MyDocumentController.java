//package com.petproject.controller;
//
//import com.petproject.model.MyDocument;
//import com.petproject.service.MyDocumentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class MyDocumentController {
//
//    @Autowired
//    private MyDocumentService myDocumentService;
//
//    @GetMapping("/document")
//    public List<MyDocument> getMyDocumentsFromElasticSearch() {
//        return myDocumentService.getMyDocumentsFromElasticSearch();
//    }
//
//    @PostMapping("/document")
//    public void saveIntoElasticSearch(@RequestBody final List<MyDocument> myDocuments) {
//        myDocumentService.indexDocuments(myDocuments);
//    }
//}

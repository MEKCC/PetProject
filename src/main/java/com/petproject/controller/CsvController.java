package com.petproject.controller;

import com.petproject.service.CsvService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvController {

    @Autowired
    private CsvService csvService;

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        csvService.exportToCsv(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return csvService.processCsv(file);
    }
}

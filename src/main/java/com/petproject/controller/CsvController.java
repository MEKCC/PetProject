package com.petproject.controller;

import com.petproject.model.CsvModel;
import com.petproject.service.CsvService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CsvController {

    @Autowired
    private CsvService csvService;

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        String filename = "test.csv";

        // Отримуємо дані з сервісу
        List<CsvModel> data = csvService.fetchAll();

        // Викликаємо метод сервісу для експорту
        csvService.exportToCsv(data, response, filename);
    }
}

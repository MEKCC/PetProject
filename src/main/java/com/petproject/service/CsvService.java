package com.petproject.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import com.petproject.model.CsvModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

    public List<CsvModel> fetchAll() {
        return List.of(new CsvModel("Max", 32), new CsvModel("Julia", 29));
    }

    public void exportToCsv(HttpServletResponse response) throws Exception {
        String filename = "test.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        new StatefulBeanToCsvBuilder<>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build()
            .write(fetchAll());
    }

    public ResponseEntity<String> processCsv(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<CsvModel> csvModels = processCsvFile(new CSVReader(reader).readAll());
            // Add logic for using or saving CsvModel objects if needed
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException | CsvException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading the file");
        }
    }

    private List<CsvModel> processCsvFile(List<String[]> csvData) {
        List<CsvModel> csvModels = new ArrayList<>();
        boolean firstRow = true;
        String[] headers = null;

        for (String[] row : csvData) {
            if (firstRow) {
                headers = row;
                firstRow = false;
            } else if (row.length >= 2) {
                Map<String, String> rowData = new HashMap<>();
                for (int i = 0; i < Math.min(row.length, headers.length); i++) {
                    rowData.put(headers[i], row[i]);
                }
                csvModels.add(createCsvModelFromMap(rowData));
            }
        }

        return csvModels;
    }

    private CsvModel createCsvModelFromMap(Map<String, String> rowData) {
        return new CsvModel(rowData.get("NAME"), Integer.parseInt(rowData.get("AGE")));
    }
}

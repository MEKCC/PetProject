package com.petproject.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.petproject.model.CsvModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvService {

    public List<CsvModel> fetchAll() {
        return List.of(new CsvModel("Max", 32), new CsvModel("Julia", 29));
    }

    public void exportToCsv(List<CsvModel> data, HttpServletResponse response, String filename) throws Exception {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<CsvModel> writer =
            new StatefulBeanToCsvBuilder<CsvModel>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false).build();

        // write data to csv file
        writer.write(data);
    }
}

package com.petproject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "myindex")
public class MyDocument {

    @Id
    private String id;
    private String field1;
    private String field2;

}

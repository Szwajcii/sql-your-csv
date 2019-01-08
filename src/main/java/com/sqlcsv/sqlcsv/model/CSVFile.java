package com.sqlcsv.sqlcsv.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CSVFile {
    private String name;
    private List<Row> rows = new ArrayList<>();
}

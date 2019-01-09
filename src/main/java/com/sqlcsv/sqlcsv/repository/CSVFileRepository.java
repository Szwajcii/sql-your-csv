package com.sqlcsv.sqlcsv.repository;

import com.sqlcsv.sqlcsv.model.Table;
import com.sqlcsv.sqlcsv.service.CSVFileRepositoryPredicates;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Repository
public class CSVFileRepository implements CSVFileRepositoryInterface {

    private List<Table> files;

    public CSVFileRepository() {
        this.files = new ArrayList<>();
    }

    @Override
    public Table save(Table file) {
       boolean found = files
               .stream()
               .anyMatch(x -> Objects.deepEquals(x, file));

       if (!found) {
           files.add(file);
       }
       return file;
    }

    @Override
    public Optional<Table> getById(int id) {

        if (idIsNotInBound(files, id)) {
            return Optional.empty();
        }
        return Optional.of(files.get(id));
    }

    @Override
    public List<Table> getAll() {
        return files;
    }

    @Override
    public boolean deleteById(int id) {
        if (idIsNotInBound(files, id)) {
            return false;
        }
        files.remove(id);
        return true;
    }

    @Override
    public List<Table> findByName(String name) {
        return CSVFileRepositoryPredicates.filterCSVFiles(files, CSVFileRepositoryPredicates.isSameName(name));
    }

    private boolean idIsNotInBound(List<Table> files, int id) {
        return id < 0 || id >= files.size();
    }
}

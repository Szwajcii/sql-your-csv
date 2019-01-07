package com.sqlcsv.sqlcsv.repository;

import com.sqlcsv.sqlcsv.model.CSVFile;
import com.sqlcsv.sqlcsv.service.CSVFileRepositoryPredicates;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@Repository
public class CSVFileRepository implements CSVFileRepositoryInterface {
//MOVE LOGIC TO SERVICE CLASS
    private List<CSVFile> files;

    @Override
    public CSVFile save(CSVFile file) {
       boolean found = files
               .stream()
               .anyMatch(x -> Objects.deepEquals(x, file));

       if (!found) {
           files.add(file);
       }
       return file;
    }

    @Override
    public Optional<CSVFile> getById(int id) {

        if (idIsNotInBound(files, id)) {
            return Optional.empty();
        }
        return Optional.of(files.get(id));
    }

    @Override
    public List<CSVFile> getAll() {
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
    public List<CSVFile> findByName(String name) {
        return CSVFileRepositoryPredicates.filterCSVFiles(files, CSVFileRepositoryPredicates.isSameName(name));
    }

    private boolean idIsNotInBound(List<CSVFile> files, int id) {
        return id < 0 || id >= files.size();
    }
}

package com.example.pfe.controller;

import com.example.pfe.entity.DisplayEntity;
import com.example.pfe.repository.DisplayRepository;
import com.google.common.io.Resources;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

@RestController
@CrossOrigin
@RequestMapping("/api/displays")
public class DisplayController {

    @Autowired
    DisplayRepository repository;

    @GetMapping("/filter")
    Page<DisplayEntity> doFilter(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        PageRequest request = new PageRequest(page > 0 ? page - 1 : 0, size, new Sort("id").descending());

        return repository.filter("%" + keyword + "%", request);
    }

    @PostMapping("/add")
    DisplayEntity addNew(@RequestBody DisplayEntity entity) {
        return repository.save(entity);
    }

    @PostMapping("/update/{id}")
    DisplayEntity updateItem(@PathVariable Long id, @RequestBody DisplayEntity model) {
        DisplayEntity entity = repository.getOne(id);

        entity.setProgName(model.getProgName());
        entity.setDisplay(model.getDisplay());
        entity.setNomTable(model.getNomTable());
        entity.setDescSol(model.getDescSol());
        entity.setIndiceGravity(model.getIndiceGravity());

        return repository.save(entity);
    }

    @PostMapping("/delete/{id}")
    void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/get/{id}")
    DisplayEntity getItem(@PathVariable Long id) {
        return repository.getOne(id);
    }

    @GetMapping("/clear")
    ResponseEntity<String> clearData() {
        try {
            repository.deleteAll();

            return ResponseEntity.ok().body("ok!");
        } catch (Exception e) {

            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PostMapping("/import")
    ResponseEntity<String> runImport() {

        try {

            String filePath = Resources.getResource("Displays.xlsx").getPath();
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            ArrayList<DisplayEntity> entities = new ArrayList<>();
            Integer bulkSize = 100;
            Integer rowIndex = 0;

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                DisplayEntity entity = new DisplayEntity();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String str = "";
                    Double number = 0d;

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            number = cell.getNumericCellValue();
                            str = "";
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            str = " ";
                            number = 0d;
                            break;
                        case Cell.CELL_TYPE_STRING:
                            str = cell.getStringCellValue();
                            number = 0d;
                            break;
                    }

                    str = str.trim();

                    switch (cell.getColumnIndex()) {
                        case 0:
                            entity.setProgName(str);
                            break;
                        case 1:
                            entity.setDisplay(str);
                            break;
                        case 2:
                            entity.setError_Name(str);
                            break;
                        case 3:
                            entity.setNomTable(str);
                            break;
                        case 4:
                            entity.setDescSol(str);
                            break;
                        case 5:
                            entity.setIndiceGravity(number);
                            break;
                    }
                }

                if (rowIndex > 0) {
                    entities.add(entity);

                    if (rowIndex % bulkSize == 0) {
                        repository.saveAll(entities);
                        entities.clear();
                    }
                }

                rowIndex++;

            }

            repository.saveAll(entities);

            file.close();

            return ResponseEntity.ok().body("ok!");
        } catch (Exception e) {

            return ResponseEntity.ok().body(e.getMessage());
        }

    }

}

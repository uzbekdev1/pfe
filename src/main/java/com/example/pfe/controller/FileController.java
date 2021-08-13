package com.example.pfe.controller;

import com.example.pfe.dto.KeyValuePair;
import com.example.pfe.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    FileService storageService;

    @GetMapping("/list")
    ResponseEntity<ArrayList<KeyValuePair>> getList() {

        ArrayList<KeyValuePair> files = new ArrayList<>();
        List<String> fileNames = storageService.getAll();

        for (String fileName : fileNames) {

            File file = new File(fileName);
            String fileUrl = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", file.getName()).build().toString();

            files.add(new KeyValuePair(file.getName(), fileUrl));
        }

        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/get")
    ResponseEntity<Resource> getFile(@RequestParam(name = "file") String file) {

        Resource ent = storageService.getResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ent.getFilename() + "\"")
                .body(ent);
    }

    @GetMapping("/delete")
    ResponseEntity<Boolean> deleteFile(@RequestParam(name = "file") String file) {

        Path fileName = storageService.getFile(file);

        try {
            Files.delete(fileName);

            return ResponseEntity.ok().body(true);
        } catch (IOException e) {

        }

        return ResponseEntity.ok().body(false);
    }

}

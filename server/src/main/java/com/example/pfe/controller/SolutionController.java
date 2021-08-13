package com.example.pfe.controller;

import com.example.pfe.dto.ScanBlock;
import com.example.pfe.dto.ScanResponse;
import com.example.pfe.entity.DisplayEntity;
import com.example.pfe.repository.DisplayRepository;
import com.example.pfe.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/solutions")
public class SolutionController {

    private static final String errorPattern = "FIN ANORMALE";
    private static final String blockPattern = "STEP";
    private static final String spacePattern = " ";
    private static final String dashPattern = "-";
    private static final String starPattern = "*";

    @Autowired
    DisplayRepository repository;

    @Autowired
    FileService storageService;

    @SuppressWarnings("Duplicates")
    @PostMapping("/stream-scan")
    ScanResponse streamScan(@RequestParam("file") MultipartFile file, @RequestParam("copy") Boolean copy) {

        ScanResponse response = new ScanResponse();

        try {

            //saved
            if (copy.booleanValue()) {
                storageService.copy(file);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), Charset.forName("ISO-8859-1")));
            ArrayList<ScanBlock> blocks = new ArrayList<>();
            ScanBlock block = null;
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith(starPattern) &&
                        line.indexOf(spacePattern) != 0 && line.indexOf(dashPattern) != 0 &&
                        line.contains(blockPattern) &&
                        line.endsWith(starPattern)) {

                    if (block != null) {

                        if (block.getTake()) {

                            blocks.add(block);
                        }

                    }

                    block = new ScanBlock();
                }

                if (block != null) {

                    if (line.contains(errorPattern)) {

                        block.setTake(true);
                    }

                    block.appendLine(line);
                }
            }

            if (block != null) {

                if (block.getTake()) {

                    blocks.add(block);
                }

            }

            if (blocks.size() > 0) {
                for (ScanBlock item : blocks) {

                    response.appendLines(item.getLines());

                    for (String line2 : item.getLines()) {

                        String[] slicedSolutions = line2.split(dashPattern);

                        if (slicedSolutions.length >= 2 && slicedSolutions[0].length() > 3 && slicedSolutions[1].length() == 8) {

                            String progName = slicedSolutions[0].substring(slicedSolutions[0].length() - 3);
                            String display = slicedSolutions[1];
                            DisplayEntity entity = repository.find(progName, display);

                            if (entity != null && entity.getIndiceGravity() > 0) {
                                response.addEntitiy(entity);
                            }

                        }

                    }
                }
            } else {
                response.setError("Aucune Fin Anormale n’a été détectée dans votre fichier");
            }

        } catch (Exception e) {
            response.setError("Le fichier semble être corrompu. Veuillez vérifier votre fichier.");
        }

        return response;
    }


    @SuppressWarnings("Duplicates")
    @PostMapping("/file-scan")
    ScanResponse fileScan(@RequestParam("file") String file) {

        ScanResponse response = new ScanResponse();

        try {

            List<String> lines = Files.readAllLines(storageService.getFile(file), Charset.forName("ISO-8859-1"));
            ArrayList<ScanBlock> blocks = new ArrayList<>();
            ScanBlock block = null;

            for (String line : lines) {

                if (line.startsWith(starPattern) &&
                        line.indexOf(spacePattern) != 0 && line.indexOf(dashPattern) != 0 &&
                        line.contains(blockPattern) &&
                        line.endsWith(starPattern)) {

                    if (block != null) {

                        if (block.getTake()) {

                            blocks.add(block);
                        }

                    }

                    block = new ScanBlock();
                }

                if (block != null) {

                    if (line.contains(errorPattern)) {

                        block.setTake(true);
                    }

                    block.appendLine(line);
                }
            }

            if (block != null) {

                if (block.getTake()) {

                    blocks.add(block);
                }

            }

            if (blocks.size() > 0) {
                for (ScanBlock item : blocks) {

                    response.appendLines(item.getLines());

                    for (String line2 : item.getLines()) {

                        String[] slicedSolutions = line2.split(dashPattern);

                        if (slicedSolutions.length >= 2 && slicedSolutions[0].length() > 3 && slicedSolutions[1].length() == 8) {

                            String progName = slicedSolutions[0].substring(slicedSolutions[0].length() - 3);
                            String display = slicedSolutions[1];
                            DisplayEntity entity = repository.find(progName, display);

                            if (entity != null && entity.getIndiceGravity() > 0) {
                                response.addEntitiy(entity);
                            }

                        }

                    }
                }
            } else {
                response.setError("Aucune Fin Anormale n’a été détectée dans votre fichier");
            }

        } catch (Exception e) {
            response.setError("Le fichier semble être corrompu. Veuillez vérifier votre fichier.");
        }

        return response;
    }

}
